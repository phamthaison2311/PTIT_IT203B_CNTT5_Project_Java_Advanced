package model;

public class Category {
    private int idCategory;
    private String categoryName;

    public int getId() {
        return idCategory;
    }

    public void setId(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return categoryName;
    }

    public void setName(String categoryName) {
        this.categoryName = categoryName;
    }
}