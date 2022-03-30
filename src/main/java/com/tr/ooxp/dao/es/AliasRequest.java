package com.tr.ooxp.dao.es;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AliasRequest {

    private static abstract class AbstractAction {
    }

    @Data
    @AllArgsConstructor
    private static class Action {
        private String index;
        private String alias;
    }

    @Data
    public static class AddAction extends AbstractAction {
        private Action add;

        public AddAction(final String index, final String alias) {
            add = new Action(index, alias);
        }
    }

    @Data
    public static class RemoveAction extends AbstractAction {
        public Action remove;

        public RemoveAction(final String index, final String alias) {
            remove = new Action(index, alias);
        }
    }

    private List<AbstractAction> actions = new ArrayList<>();

    public void addAction(final AbstractAction action) {
        actions.add(action);
    }
}
