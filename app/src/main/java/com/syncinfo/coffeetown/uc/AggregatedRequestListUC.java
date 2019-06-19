package com.syncinfo.coffeetown.uc;

import com.syncinfo.coffeetown.model.Command;
import com.syncinfo.coffeetown.model.Model;
import com.syncinfo.coffeetown.model.Offering;
import com.syncinfo.coffeetown.model.ProductCategory;
import com.syncinfo.coffeetown.model.Request;
import com.syncinfo.mvc.uc.impl.BasicListUC;
import com.syncinfo.mvc.uc.impl.BasicListUCAdapter;
import com.syncinfo.util.ListenerManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mmartins on 2018-02-26.
 */

public class AggregatedRequestListUC extends BasicListUC<AggregatedRequestListUC.AggregatedRequest> {

    public static class AggregatedRequest {
        public final Offering offering;
        int count = 0;
        double total = 0;
        private List<Request> requests[] = new List[Request.Status.values().length];

        AggregatedRequest(Request request) {
            this.offering = request.getOffering();
            add(request);
        }

        void add(Request request) {
            add(request, request.getStatus());
            if (Request.Status.CANCELLED != request.getStatus()) {
                this.total += request.getOffering().getPrice();
            }
        }

        private void add(Request request, Request.Status s) {
            if (requests[s.ordinal()] == null) {
                requests[s.ordinal()] = new ArrayList<>();
            }

            this.count++;
            requests[s.ordinal()].add(request);
        }

        public List<Request> getRequests(Request.Status status) {
            List<Request> result = this.requests[status.ordinal()];
            if (null != result) {
                return result;
            }
            else {
                return Collections.EMPTY_LIST;
            }
        }

        public String getStatus() {
            List<Request> notDelivered = getRequests(Request.Status.NOT_DELIVERED);
            List<Request> cancelled = getRequests(Request.Status.CANCELLED);
            List<Request> delivered = getRequests(Request.Status.DELIVERED);
            StringBuilder builder = new StringBuilder(10);
            builder.append(this.count).append('/').
                    append(delivered.size()).append('/').
                    append(notDelivered.size()).append('/').
                    append(cancelled.size());
            return builder.toString();
        }

        public double getTotal() {
            return total;
        }
    }

    public interface OnCommandSetListener {
        void onCommandSelected(Command command);
    }

    private Command command = null;
    private ProductCategory productCategory= null;
    private ListenerManager<OnCommandSetListener> onCommandSetListener = new ListenerManager<>();

    public AggregatedRequestListUC () { super(); resetAdapter(); }

    @Override
    public void resetAdapter() {
        createNewAdapter();
    }

    public void listByCommand(int commandId) {
        listByCommand(Model.getInstance().getCommandDAO().getById(commandId));
    }

    public void listByCommand(Command command) {
        this.command = command;
        createNewAdapter();
        this.onCommandSetListener.notify(l -> l.onCommandSelected(command));
    }

    public void filterByCategory(int productCategoryId) {
        filterByCategory(Model.getInstance().getProductCategoryDao().getById(productCategoryId));
    }

    public void filterByCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        createNewAdapter();
    }

    private void createNewAdapter() {
        Collection<Request> requests = null;
        if (null == this.productCategory) {
            if (null == this.command) {
                requests = Collections.EMPTY_LIST;
            }
            else {
                requests = new ArrayList<>();
                Model.getInstance().getRequestDAO().findByCommandId(this.command.getId(), requests);
            }
        }
        else {
            if (null == this.command) {
                requests = Collections.EMPTY_LIST;
            }
            else {
                requests = new ArrayList<>();
                Model.getInstance().getRequestDAO().
                        findByCommandIdAndCategoryId(this.command.getId(), this.productCategory.getId(), requests);
            }
        }

        Map<Integer, AggregatedRequest> aggregatedRequestMap = new HashMap<>();
        for (Request r: requests) {
            AggregatedRequest a = aggregatedRequestMap.get(r.getOffering().getId());
            if (null == a) {
                aggregatedRequestMap.put(r.getOffering().getId(), new AggregatedRequest(r));
            }
            else {
                a.add(r);
            }
        }

        setAdapter(new BasicListUCAdapter<>(aggregatedRequestMap.values()));
    }

    public Command getCommand() {
        return command;
    }

    public void registerOnCommandSetListener(OnCommandSetListener onCommandSetListener) {
        this.onCommandSetListener.registerListener(onCommandSetListener);
    }
}
