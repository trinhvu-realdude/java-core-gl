package greatlearning.miniproject.model;

import java.util.List;

public class MenuDetails {
    private int id;
    private int menuId;
    private List<Integer> itemIdList;

    public MenuDetails(int menuId, List<Integer> itemIdList) {
        this.menuId = menuId;
        this.itemIdList = itemIdList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public List<Integer> getItemIdList() {
        return itemIdList;
    }

    public void setItemIdList(List<Integer> itemIdList) {
        this.itemIdList = itemIdList;
    }
}
