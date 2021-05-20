package client.View;

public class SelectState {
    private int editSelect;

    public SelectState(){
        this.editSelect = -1;
    }

    public void setEditSelect(int editSelect) {
        this.editSelect = editSelect;
        System.out.println(editSelect);
    }

    public int getEditSelect() {
        return editSelect;
    }
}
