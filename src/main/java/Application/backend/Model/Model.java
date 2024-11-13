package Application.backend.Model;

import Application.Views.FactoryViews;

public class Model {
    private static Model model;
    private final FactoryViews factoryViews;


    public Model() {
        this.factoryViews = new FactoryViews();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public FactoryViews getFactoryViews() {
        return factoryViews;
    }
}
