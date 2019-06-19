package com.syncinfo.mvc.uc;

import com.syncinfo.util.Matcher;

import java.util.Collection;

/**
 * <p>ListUC is a basic use case where a list of a target entity is shown to user and the user can select one or more
 * instances to perform other UC with them.</p> <p>As any other UC, it is expected that a User Interface (UI) provides
 * user input and receives the system results.</p>
 * <p>The ListUC also provides the list of </p>user, like create a new entity or update or remove an existent entity
 * selected.<p> The UC considers it has:<p> - a source of target entities to show to user that is abstract to it and
 * encapsulated in the Adapter interface;<p> - a way to allow user to select specific entity instances from adapter to
 * perform other UC of them. </p>
 */
public interface ListUC<TargetEntity> {

    /**
     * @return the adapter so that the UI can show the list to user
     */
    Adapter<TargetEntity> getAdapter();

    ChoiceMode getChoiceMode();

    void setItemSelected(int position, boolean state);

    void unselectAllItems();

    boolean isSelected(int position);

    Collection<Integer> positionsSelected();
    int positionSelected();

    boolean hasSelection();

    /**
     * Reset adapter force the recreation of the adapter used by the ListUC. All observers must be registered again.
     */
    void resetAdapter();

    TargetEntity getItemSelected();

    <S extends Collection<TargetEntity>> S getItemsSelected(S result);
    TargetEntity[] getItemsSelected(TargetEntity[] array);

    void registerOnItemSelectedListener(Observer.OnItemSelectedListener<TargetEntity> listener);

    void registerOnUnselectedAllItemsListener(Observer.OnUnselectedAllItemsListener<TargetEntity> listener);

    void registerOnResetAdapterListener(Observer.OnResetAdapterListener<TargetEntity> listener);

    void unregisterOnResetAdapterListener(Observer.OnResetAdapterListener<TargetEntity> listener);

    void unregisterOnItemSelectedListener(Observer.OnItemSelectedListener<TargetEntity> listener);

    void unregisterOnUnselectedAllItemsListener(Observer.OnUnselectedAllItemsListener<TargetEntity> listener);

    enum ChoiceMode {SINGLE_SELECTION, MULTI_SELECTION}

    interface Adapter<TargetEntity> {
        <C extends Collection<TargetEntity>> C findAll(Matcher<TargetEntity> matcher, C result);

        /**
         * @return true if the adapter has all the instances of just part of them (in case paging is needed)
         */
        boolean hasAllInstances();

        boolean isEmpty();

        int getCount();

        TargetEntity getItem(int position);

        int getPosition(TargetEntity item);

      void registerObserver(Observer observer);

        void unregisterObserver(Observer observer);

        /**
         * Interface to notify UI that the underneath data source was modified and the UI should be updated.
         */
        interface Observer {
            void onChanged();
        }
    }

    /**
     * Observers are invoked after the operation happens
     */
    interface Observer {
        interface OnItemSelectedListener<TargetEntity> {
            void onItemSelected(int index, boolean selected, Adapter<TargetEntity> adapter);
        }

        interface OnUnselectedAllItemsListener<TargetEntity> {
            void onUnselectedAllItems(Collection<Integer> unselectedIndexes,
                                      Adapter<TargetEntity> adapter);
        }

        interface OnResetAdapterListener<TargetEntity> {
            /**
             * Notifies a new adapter was created. If the reset operation can be performed without creating a new
             * adapter, then only the Adapter.Observer.onChanged() method is invoked.
             *
             * @param adapter new adapter created.
             */
            void onResetAdapter(Adapter<TargetEntity> adapter);
        }
    }

    interface ResultHandler {
        interface NoResult { void handle(); }
        interface SingleResult<ResultType> { void handle(ResultType result); }
    }

    /**
     * Each UC implementation must specify the launcher needed for invoking another UC.
     */
    interface Launcher {
        interface NoInstance {
            void launch();
        }

        interface SingleInstance<TargetEntity> {
            void launch(TargetEntity instance);
        }

        interface MultiInstance<TargetEntity> {
            void launch(Collection<TargetEntity> instances);
        }
    }
}
