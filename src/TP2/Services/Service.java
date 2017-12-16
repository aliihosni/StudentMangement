package TP2.Services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import TP2.Models.Absence;
import TP2.Models.Classe;
import TP2.Models.Cours;
import TP2.Models.Student;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Service {


    private String preUrl = "http://localhost:3000/";


    public Service(){

    }



    // HTTP GET request
    public String sendGet(String url) throws Exception {



        URL obj = new URL(this.preUrl + url + ".json");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + this.preUrl + url + ".json");
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();

    }




    // HTTP POST request
    public Integer sendPost(String url, Map<String, String> parameters, String method) throws Exception {


        URL obj = new URL(null, this.preUrl + url + ".json", new sun.net.www.protocol.http.Handler());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");




        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);


        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(getParamsString(parameters));
        out.flush();
        out.close();


        int responseCode = con.getResponseCode();





        System.out.println("\nSending 'POST' request to URL : " + this.preUrl + url + ".json");
        System.out.println("Post parameters : " + parameters);
        System.out.println("Response Code : " + responseCode);

//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//
//        //print result
//        System.out.println(response.toString());



        return responseCode;

    }



    // HTTP Del request
    public Integer sendDelete(String url, Integer id) throws Exception {


        URL obj = new URL(null, this.preUrl + url + "/"+id+".json", new sun.net.www.protocol.http.Handler());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("DELETE");




        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);


        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.flush();
        out.close();


        int responseCode = con.getResponseCode();





        System.out.println("\nSending 'DELETE' request to URL : " + this.preUrl + url + ".json");
        System.out.println("Response Code : " + responseCode);

        return responseCode;

    }


    public String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }








    public ArrayList<Classe> getClasses(){

        ArrayList<Classe> classes = new ArrayList<>();

        JSONArray data = getJSONArray("classes");

        for(int i = 0 ; i < data.size() ; i++){


            JSONObject node = (JSONObject)data.get(i);
            Classe classRoom = new Classe(Math.toIntExact((Long) node.get("id")),(String) node.get("name"));
            classes.add(classRoom);
        }

        return classes;
    }


    public ArrayList<Cours> getCours(){

        ArrayList<Cours> courses = new ArrayList<>();

        JSONArray data = getJSONArray("cours");

        for(int i = 0 ; i < data.size() ; i++){


            JSONObject node = (JSONObject)data.get(i);
            Cours cours = new Cours(Math.toIntExact((Long) node.get("id")),(String) node.get("name"),Math.toIntExact((Long) node.get("classe_id")));
            courses.add(cours);
        }

        return courses;
    }

    public ArrayList<Cours> getCoursByClasse(Integer classeId){

        ArrayList<Cours> courses = new ArrayList<>();

        JSONArray data = getJSONArray("cours/classe/" + classeId);

        for(int i = 0 ; i < data.size() ; i++){


            JSONObject node = (JSONObject)data.get(i);
            Cours cours = new Cours(Math.toIntExact((Long) node.get("id")),(String) node.get("name"),Math.toIntExact((Long) node.get("classe_id")));
            courses.add(cours);
        }

        return courses;
    }


    public ArrayList<Student> getStudentsByClasse(Integer classeId){

        ArrayList<Student> students = new ArrayList<>();

        JSONArray data = getJSONArray("students/classe/" + classeId);

        for(int i = 0 ; i < data.size() ; i++){


            JSONObject node = (JSONObject)data.get(i);
            Student student = new Student(Math.toIntExact((Long) node.get("id")),(String) node.get("name"),Math.toIntExact((Long) node.get("classe_id")));
            students.add(student);
        }

        return students;
    }


    public ArrayList<Student> getStudents(){

        ArrayList<Student> students = new ArrayList<>();

        JSONArray data = getJSONArray("students");

        for(int i = 0 ; i < data.size() ; i++){


            JSONObject node = (JSONObject)data.get(i);
            Student student = new Student(Math.toIntExact((Long) node.get("id")),(String) node.get("name"),Math.toIntExact((Long) node.get("classe_id")));
            students.add(student);
        }

        return students;
    }

    public ArrayList<Student> getStudentsByName(String studentName){

        ArrayList<Student> students = new ArrayList<>();

        JSONArray data = getJSONArray("students/name/" + studentName    );

        for(int i = 0 ; i < data.size() ; i++){


            JSONObject node = (JSONObject)data.get(i);
            Student student = new Student(Math.toIntExact((Long) node.get("id")),(String) node.get("name"),Math.toIntExact((Long) node.get("classe_id")));
            students.add(student);
        }

        return students;
    }


    public ArrayList<Absence> getAbsences() throws java.text.ParseException {

        ArrayList<Absence> absences = new ArrayList<>();

        JSONArray data = getJSONArray("absences");

        for(int i = 0 ; i < data.size() ; i++){


            JSONObject node = (JSONObject)data.get(i);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse( (String) node.get("date"));
            Absence absence = new Absence(Math.toIntExact((Long) node.get("id")),Math.toIntExact((Long) node.get("status")),(String) node.get("justif"), date,Math.toIntExact((Long) node.get("student_id")),Math.toIntExact((Long) node.get("cour_id")));
            absences.add(absence);
        }

        return absences;
    }

    public ArrayList<Absence> getAbsencesByCours(String idcours){

        ArrayList<Absence> absences = new ArrayList<>();

        JSONArray data = getJSONArray("absences/cours/" + idcours);

        for(int i = 0 ; i < data.size() ; i++){


            JSONObject node = (JSONObject)data.get(i);
            Absence absence = new Absence(Math.toIntExact((Long) node.get("id")),Math.toIntExact((Long) node.get("status")),(String) node.get("justif"),(Date) node.get("date"),Math.toIntExact((Long) node.get("student_id")),Math.toIntExact((Long) node.get("cour_id")));
            absences.add(absence);
        }

        return absences;
    }















    public JSONArray getJSONArray(String url){
        JSONArray array = new JSONArray();
        try {
            String stringJSON = this.sendGet(url);
            return this.toJSONArray(stringJSON);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public JSONArray toJSONArray(String jsonString){
        JSONArray data = new JSONArray();
        try {
            JSONParser helper = new JSONParser();
            return (JSONArray)helper.parse(jsonString);
        } catch (ParseException parse) {
            // Invalid syntax

        }
        return data;
    }
}
