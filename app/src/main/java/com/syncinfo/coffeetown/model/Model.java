package com.syncinfo.coffeetown.model;

import com.syncinfo.android.coffeetown.view.OfferingListViewFragment;
import com.syncinfo.android.coffeetown.view.OfferingSelectionViewFragment;
import com.syncinfo.android.coffeetown.view.TableListPerWaiterViewFragment;
import com.syncinfo.coffeetown.model.dao.CommandDAO;
import com.syncinfo.coffeetown.model.dao.OfferingDAO;
import com.syncinfo.coffeetown.model.dao.ProductCategoryDao;
import com.syncinfo.coffeetown.model.dao.ProductDao;
import com.syncinfo.coffeetown.model.dao.RequestDAO;
import com.syncinfo.coffeetown.model.dao.TableDao;
import com.syncinfo.coffeetown.model.dao.UserViewDao;
import com.syncinfo.coffeetown.model.dao.WaiterDao;
import com.syncinfo.coffeetown.model.dao.impl.InMemoryCommandDAO;
import com.syncinfo.coffeetown.model.dao.impl.InMemoryOfferingDAO;
import com.syncinfo.coffeetown.model.dao.impl.InMemoryProductCategoryDao;
import com.syncinfo.coffeetown.model.dao.impl.InMemoryProductDAO;
import com.syncinfo.coffeetown.model.dao.impl.InMemoryRequestDAO;
import com.syncinfo.coffeetown.model.dao.impl.InMemoryTableDao;
import com.syncinfo.coffeetown.model.dao.impl.InMemoryUserViewDao;
import com.syncinfo.coffeetown.model.dao.impl.InMemoryWaiterDao;

/**
 * Created by mmartins on 2017-12-30.
 */

public class Model {
    private static Model singleton = new Model();
    private WaiterDao waiterDao = new InMemoryWaiterDao();
    private TableDao tableDao = new InMemoryTableDao();
    private UserViewDao userViewDao = new InMemoryUserViewDao();
    private ProductCategoryDao productCategoryDao = new InMemoryProductCategoryDao();
    private ProductDao productDao = new InMemoryProductDAO();
    private OfferingDAO offeringDAO = new InMemoryOfferingDAO();
    private RequestDAO requestDAO = new InMemoryRequestDAO();
    private CommandDAO commandDAO = new InMemoryCommandDAO();

    private Model() {
        Waiter carina = new Waiter(0, "Carina");
        waiterDao.save(carina);
        Table table1 = new Table(0, "Mesa 1", carina);
        tableDao.save(table1);
        tableDao.save(new Table(0, "Mesa 2", carina));
        tableDao.save(new Table(0, "Mesa 3", null));

        Waiter taina = new Waiter(0, "Taina");
        waiterDao.save(taina);

        waiterDao.save(new Waiter(0, "Rafael"));


        Command list = new Command();
        list.setTableId(table1.getId());
        list.setLabel("Comanda 1");
        commandDAO.save(list);

        UserView userView = new UserView();
        userView.setLabel("ProductCategoryMain");
        userView.setLauncher("com.syncinfo.android.coffeetown.view.ProductCategoryMainViewFragment");
        userViewDao.save(userView);

        userView = new UserView();
        userView.setLabel("WaiterList");
        userView.setLauncher("com.syncinfo.android.coffeetown.view.WaiterListViewFragment");
        userViewDao.save(userView);

        userView = new UserView();
        userView.setLabel("TableListView");
        userView.setLauncher(TableListPerWaiterViewFragment.class);
        userViewDao.save(userView);

        userView = new UserView();
        userView.setLabel("OfferingListView");
        userView.setLauncher(OfferingListViewFragment.class);
        userViewDao.save(userView);

        userView = new UserView();
        userView.setLabel("OfferingSelectionView");
        userView.setLauncher(OfferingSelectionViewFragment.class);
        userViewDao.save(userView);

        ProductCategory beverage = new ProductCategory();
        beverage.setName("Bebidas");
        productCategoryDao.save(beverage);

        ProductCategory cake = new ProductCategory();
        cake.setName("Torta");
        productCategoryDao.save(cake);

        Product product = productDao.create();
        product.setName("Torta Chocolate fatia");
        product.addCategory(cake);
        productDao.save(product);

        Offering offering = offeringDAO.create();
        offering.setProduct(product);
        offering.setPrice(5.00);
        offeringDAO.save(offering);

        product = productDao.create();
        product.setName("Torta Chocolate inteira");
        product.addCategory(cake);
        productDao.save(product);

        offering = offeringDAO.create();
        offering.setProduct(product);
        offering.setPrice(50.00);
        offeringDAO.save(offering);
    }

    static public Model getInstance() {
        return singleton;
    }

    public TableDao getTableDao() {
        return tableDao;
    }

    public WaiterDao getWaiterDao() {
        return waiterDao;
    }

    public UserViewDao getUserViewDao() {
        return userViewDao;
    }

    public ProductCategoryDao getProductCategoryDao() {
        return productCategoryDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public OfferingDAO getOfferingDAO() {
        return offeringDAO;
    }

    public RequestDAO getRequestDAO() {
        return requestDAO;
    }

    public CommandDAO getCommandDAO () { return this.commandDAO; }
}
