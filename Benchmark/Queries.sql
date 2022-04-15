select a32f61e0_e884_11ea_ae22_f328fa2fcd28,a32f61e1_e884_11ea_ae22_f328fa2fcd28,rowid,formulaname , steps , usedinformula , components , numerator , denominator , parentid , referencerowid , stage , ruleid , rulename , ruletype , sequencename , lastupdateby , lastupdatedate , action , filetype , externalperiod , adjustment from ooxp_v9m_narsinew_251806346041127_prereportready where a32f61e1_e884_11ea_ae22_f328fa2fcd28 in ('U','P','E');;
select a32f61e0_e884_11ea_ae22_f328fa2fcd28,a32f61e1_e884_11ea_ae22_f328fa2fcd28,rowid,formulaname , steps , usedinformula , components , numerator , denominator , parentid , referencerowid , stage , ruleid , rulename , ruletype , sequencename , lastupdateby , lastupdatedate , action , filetype , externalperiod , adjustment from ooxp_v9m_narsinew_251806346041127_prereportready where filetype ='IBCM' and externalperiod='01' and adjustment='no';;
select a32f61e0_e884_11ea_ae22_f328fa2fcd28,a32f61e1_e884_11ea_ae22_f328fa2fcd28,rowid,formulaname , steps , usedinformula , components , numerator , denominator , parentid , referencerowid , stage , ruleid , rulename , ruletype , sequencename , lastupdateby , lastupdatedate , action , filetype , externalperiod , adjustment from ooxp_v9m_narsinew_251806346041127_prereportready ;
select a32f61e0_e884_11ea_ae22_f328fa2fcd28,a32f61e1_e884_11ea_ae22_f328fa2fcd28,rowid,formulaname , steps , usedinformula , components , numerator , denominator , parentid , referencerowid , stage , ruleid , rulename , ruletype , sequencename , lastupdateby , lastupdatedate , action , filetype , externalperiod , adjustment from ooxp_v9m_narsinew_251806346041127_prereportready where a32f61e1_e884_11ea_ae22_f328fa2fcd2 in( "U","E","P";
select year, period, rowid, formulaname, steps, usedinformula, components, numerator, denominator, parentid, referencerowid, stage, ruleid, rulename, ruletype, sequencename, lastupdateby, lastupdatedate, action, h_275d85a2_a156_11e8_9513_8592c22958bd_value, h_275d85a2_a156_11e8_9513_8592c22958bd_level, h_275d85a2_a156_11e8_9513_8592c22958bd_level1, h_275d85a2_a156_11e8_9513_8592c22958bd_level2, h_275d85a2_a156_11e8_9513_8592c22958bd_level3, h_275d85a2_a156_11e8_9513_8592c22958bd_level4, h_275d85a3_a156_11e8_9513_8592c22958bd_value, h_275d85a3_a156_11e8_9513_8592c22958bd_level, h_275d85a3_a156_11e8_9513_8592c22958bd_level1, h_275d85a3_a156_11e8_9513_8592c22958bd_level2, h_275d85a3_a156_11e8_9513_8592c22958bd_level3, h_275d85a3_a156_11e8_9513_8592c22958bd_level4, h_275d85a3_a156_11e8_9513_8592c22958bd_level5, h_275d85a3_a156_11e8_9513_8592c22958bd_level6, filetype, externalperiod, adjustment from ooxp_v9m_narsinew_251870467520764_prereportready;
select year, period, rowid, formulaname, steps, usedinformula, components, numerator, denominator, parentid, referencerowid, stage, ruleid, rulename, ruletype, sequencename, lastupdateby, lastupdatedate, action, h_275d85a2_a156_11e8_9513_8592c22958bd_value, h_275d85a2_a156_11e8_9513_8592c22958bd_level, h_275d85a2_a156_11e8_9513_8592c22958bd_level1, h_275d85a2_a156_11e8_9513_8592c22958bd_level2, h_275d85a2_a156_11e8_9513_8592c22958bd_level3, h_275d85a2_a156_11e8_9513_8592c22958bd_level4, h_275d85a3_a156_11e8_9513_8592c22958bd_value, h_275d85a3_a156_11e8_9513_8592c22958bd_level, h_275d85a3_a156_11e8_9513_8592c22958bd_level1, h_275d85a3_a156_11e8_9513_8592c22958bd_level2, h_275d85a3_a156_11e8_9513_8592c22958bd_level3, h_275d85a3_a156_11e8_9513_8592c22958bd_level4, h_275d85a3_a156_11e8_9513_8592c22958bd_level5, h_275d85a3_a156_11e8_9513_8592c22958bd_level6, filetype, externalperiod, adjustment from ooxp_v9m_narsinew_251870467520764_prereportready where period=1 and year=2016 and rowid='10'
select year, period, rowid, formulaname, steps, usedinformula, components, numerator, denominator, parentid, referencerowid, stage, filetype, externalperiod, adjustment from ooxp_v9m_narsinew_251870467520764_prereportready where stage='I';
select year, rowid, formulaname, steps, usedinformula, components, numerator, denominator, parentid, referencerowid, stage, ruleid, rulename, ruletype, sequencename, lastupdateby, lastupdatedate, action from ooxp_v9m_narsinew_faa3ff50_9190_11e8_ba52_136adbd8430e_251866745487479_postreportready where year = 2016 and stage='I';
select count(*),year,period from ooxp_v9m_narsinew_faa3ff50_9190_11e8_ba52_136adbd8430e_251866745487479_postreportready group by year,period;