package com.syncinfo.coffeetown.uc;

import com.syncinfo.coffeetown.model.Model;
import com.syncinfo.coffeetown.model.Waiter;
import com.syncinfo.mvc.uc.impl.FilterBasedListUC;

/**
 * Use case name: Waiter list<br/>
 * Description: Show a list of waiters to user and allow him to select one operation to be performed with the waiter
 * selected.<br/>
 * Normal flow:<br/>
 * <ul>
 *     <li>1. The user asks to see all active waiters</li>
 *     <li>2. The system shows all active waiters in the system</li>
 *     <li>3. The user selects one waiter from the list</li>
 *     <li>4. The system register the selection</li>
 *     <li>5. The user asks to update the waiter</li>
 *     <li>6. The system launches the Waiter Maintenance use case asking to show the selected waiter</li>
 *     <li>7. When launched UC ends then the list is reset</li>
 * </ul>
 * Alternate flows:
 * <ol>
 *     <li>Name: New waiter<br/>
 *         Starts on step 3 when user asks to create a new waiter
 *     <ul>
 *         <li>3. The user asks to create a new user</li>
 *         <li>4. The system launches the Waiter Maintenance use case asking to create a new waiter</li>
 *         <li>5. When launched UC ends then the list is reset</li>
 *     </ul>
 *     </li>
 *     <li>Name: Tables serviced by waiter<br/>
 *         Starts on step 5 when user asks to see all tables serviced by the waiter selected
 *     <ul>
 *         <li>5. The user asks to see all tables of the selected waiter</li>
 *         <li>6. The system launches the Table List use case asking to filter by given waiter</li>
 *         <li>7. normal flow from this point</li>
 *     </ul>
 *     </li>
 *
 * </ol>
 */

public class WaiterListUC extends FilterBasedListUC<Waiter> {

    /**
     * Normal flow is to show all waiters
     */
    public WaiterListUC() {
        super(result -> Model.getInstance().getWaiterDao().getAll(result));
    }
}
