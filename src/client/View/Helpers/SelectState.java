package client.View.Helpers;

public class SelectState {
    private int editSelect;
    private boolean isAdd;

    public SelectState(){
        this.editSelect = -1;
        this.isAdd = true;
    }

    public void setEditSelect(int editSelect) {
        this.editSelect = editSelect;
        System.out.println(editSelect);
    }

    public int getEditSelect() {
        return editSelect;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }
}
