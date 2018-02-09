package de.rst.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javafx.beans.property.SimpleBooleanProperty;

import java.beans.Transient;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;



public class Project {


    private Plan plan;
    private transient Charset charset;
    private SimpleBooleanProperty updateView2dProperty;




    public Project() {
        charset = Charset.forName("UTF-8");
        updateView2dProperty = new SimpleBooleanProperty();
    }


    public void saveProject(File file) {



        if (plan.getName() == null)
            plan.setName(file.getName());

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(this);



        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), charset)) {
            writer.write(json, 0, json.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }


    public Project loadProject(File file) {


        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       Project project  = gson.fromJson(reader,Project.class);
        return project;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void init() {
        plan = new Plan();
        plan.setHeight(25);
        plan.setWidth(25);
        plan.setPlanScale(5);
        plan.setZoom(55);
        plan.setRulerWidth(20);
    }


    public SimpleBooleanProperty updateView2dProperty(){
        return updateView2dProperty;
    }


    public boolean getUpdateView2d() {
        return updateView2dProperty.get();
    }


    public void setUpdateView2d(boolean update) {
        this.updateView2dProperty.set(update);
    }
}
