package de.rst.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class Project {


    private Plan plan;
    private Charset charset;


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


    public void openProject(File file) {


        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
