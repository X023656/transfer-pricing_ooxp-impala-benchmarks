from pyspark.sql import SparkSession
from pyspark import SparkContext, SparkConf
from pyathena import connect
import os

aws_access_key_id = "ASIAV3KSNBKVYNLMEGZO"
aws_secret_access_key = "hOygmcXCzOeVDmGOJhZDezhrIhrnVSbbeB/QXPIN"
aws_session_token = "IQoJb3JpZ2luX2VjEIz//////////wEaCXVzLWVhc3QtMSJGMEQCIFkFheJVA0j7IZ0K5VT4kw/PvE5c3ublWSAdzKVN2wbXAiAcxIosUST6jlHGfUiW+ah3pbcVQTXf9q/0ozVOON4IeirYAggVEAMaDDQwMjI4OTEzNDI1MSIM3aBzOwm4L2q7e+S+KrUCc9DJjwaFubTF9MPttcs98hP4ORx2a6k6Xa3xzmNisNkOZFVmrOhM/hsLliUf29wq2YM9dMtlFWEsL9rTjUuDQfSVcZW2bqeLNgsDiDWWvNFs05ujdzhxlgeMCOIgpKUDwaVX9iNg/6qC4t43U5oYGUTIhDDeI7Q+A7RNv+XxzapZ2AXDONH6/cHdwRb8Qgtf/tbV6P0v/Umcc5SPf94nDszfutZonQ3RfzrmLvP+n5lh4xzWOE72oW177ZRci2xpN9WKm3nVmXIp8QF/D4XqhOdowAiRYxfhJJoVxM50yDROkdwRxCn3yWmAWAOaX1sgVflti0x5TqjMivUPizrrzt9WDy3XPxwsWi6YTx95EgG/+m24qgTWGON9xuraS5Qj7TzqZx32zoyOK+sFgHoX/a6KyakyMLjN4ZEGOqMBLcB3hjXZYR8tvkAAEl9QDmrgg5f78CoCLaEjCRs2ptkxpOWuj4+NysuaEp9zgdrVt3z2xjYMyovZYpYIWT5eI0AGqThNG2+494TcxdUAxVRCvVK8El+zPSjd6PTHaHT96wj42W0r4Aup+XPyyoNmjPHGl31sxkmTwTsXUWHptigbAS+1pJS+uvZyXqkVINh7gKmv/FIlRUa8lnmbleNGOuxOIA=="
s3_input_path = "s3a://a204050-ooxp-poc/OOXP-Operational-Import/Operational_201601.csv"
s3_output_path = "s3a://a204050-ooxp-poc/ooxp-outbound/"

athena_table_name = "`ooxpqa`.`ooxp_report2`"

def getType(num):
    switch={
      'IntegerType':'int',
      'StringType':'string',
      'LongType':'long',
      'FloatType':'float',
      'DoubleType':'double',
      'DecimalType':'decimal' ,
      'BooleanType' : 'boolean'
      }
    return switch.get(num,'string')

if __name__ == '__main__':

    conf = SparkConf().setAppName('Athena_POC').setMaster('local[*]')
    conf.set("spark.sql.execution.arrow.pyspark.enabled", "true")
    conf.set("spark.sql.repl.eagerEval.enabled", "true")

    sc = SparkContext(conf=conf)
    sc.setSystemProperty('com.amazonaws.services.s3.enableV4', 'true')

    hadoopConf = sc._jsc.hadoopConfiguration()
    hadoopConf.set("fs.s3a.aws.credentials.provider", "org.apache.hadoop.fs.s3a.TemporaryAWSCredentialsProvider")
    hadoopConf.set('fs.s3a.access.key', aws_access_key_id)
    hadoopConf.set('fs.s3a.secret.key', aws_secret_access_key)
    hadoopConf.set("fs.s3a.session.token", aws_session_token)
    hadoopConf.set('fs.s3a.endpoint', 's3-us-east-2.amazonaws.com')
    hadoopConf.set('fs.s3a.impl', 'org.apache.hadoop.fs.s3a.S3AFileSystem')
    hadoopConf.set("parquet.enable.summary-metadata", "true")
    hadoopConf.set("mapreduce.fileoutputcommitter.algorithm.version", "2")

    spark = SparkSession(sc)
    sc.setLogLevel("INFO")
    spark.conf.set("spark.sql.execution.arrow.enabled", "true")
    try:
        df1 = spark.read.option("header", True).option("inferSchema", True).csv(s3_input_path)
        print("=====Reading From S3 ==== ")
        df1.show(10)
        print("====================Read from Aurora Database=======================")
        df_aurora = spark.read.format("jdbc").option("url", "jdbc:postgresql://database-3.cluster-ro-c0v2lxdva43p.us-east-2.rds.amazonaws.com:5432/dev").option("user", "postgres") .option("password", "Admin123").option("dbtable", "ooxp.data_locator").load().createOrReplaceTempView("my_spark")
        df_aurora1 = spark.sql("select * from my_spark")
        df_aurora1.show(10)
        print("====================Writing to S3=======================")
        df1.write.format("parquet").mode("overwrite").save(s3_output_path)
        print("======Creating Athena Table.....========")
        qry_str = "CREATE EXTERNAL TABLE " + athena_table_name + "("
        for i in df1.schema.fields:
            qry_str = qry_str + " `" + i.name + "` " + getType(str(i.dataType)) + ","
        qry_str = qry_str.rstrip(',') + ")"
        qry_str = qry_str + " ROW FORMAT SERDE   'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe' STORED AS INPUTFORMAT   'org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat' OUTPUTFORMAT   'org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat'"
        qry_str = qry_str + " LOCATION" + " 's3://a204050-ooxp-poc/ooxp-outbound/'"
        qry_str = qry_str + " TBLPROPERTIES (  'numFiles'='0',   'numRows'='-1',   'rawDataSize'='-1',   'totalSize'='0',   'transient_lastDdlTime'='1645562116')"
        print("====Connected To Athena Database=====")
        conn = connect(aws_access_key_id=aws_access_key_id,
                           aws_secret_access_key=aws_secret_access_key,
                           aws_session_token=aws_session_token,
                           s3_staging_dir="s3://a204050-ooxp-poc",
                           region_name="us-east-2").cursor()
        conn.execute(qry_str, cache_size=100, cache_expiration_time=3600)
        print("======Athena Table Created Successsful========")

    except Exception as e:
        print("Failed To Athena Create Table", e)






