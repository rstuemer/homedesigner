package de.rst.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.beans.Transient;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;



public class Project {


    private Plan plan;
    private transient Charset charset;


    public Project() {
        charset = Charset.forName("UTF-8");
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


}
