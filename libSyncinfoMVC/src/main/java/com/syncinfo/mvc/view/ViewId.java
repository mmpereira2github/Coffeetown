/**
 * @startuml interface ViewId
 * @enduml
 */
package com.syncinfo.mvc.view;

import com.syncinfo.mvc.view.impl.IntBasedViewId;
import com.syncinfo.mvc.view.impl.StringBasedViewId;

/**
 * Created by mmartins on 2018-01-01.
 */

public interface ViewId {
    class Based {
        public static ViewId onString(String id) {
            return new StringBasedViewId(id);
        }

        public static ViewId onInt(int id) {
            return new IntBasedViewId(id);
        }
    }
}
