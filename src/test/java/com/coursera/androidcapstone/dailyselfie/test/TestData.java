package com.coursera.androidcapstone.dailyselfie.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import com.coursera.androidcapstone.dailyselfie.model.CheckIn;
import com.coursera.androidcapstone.dailyselfie.model.Doctor;
import com.coursera.androidcapstone.dailyselfie.model.Medicine;
import com.coursera.androidcapstone.dailyselfie.model.Patient;
import com.coursera.androidcapstone.dailyselfie.model.Question;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is a utility class to aid in the construction of
 * Video objects with random names, urls, and durations.
 * The class also provides a facility to convert objects
 * into JSON using Jackson, which is the format that the
 * VideoSvc controller is going to expect data in for
 * integration testing.
 * 
 * @author jules
 *
 */
public class TestData {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static List<Doctor> sDoctors = new ArrayList<Doctor>();
    private static List<Patient> sPatients = new ArrayList<Patient>();

    /**
     * Construct and return a Doctor object with a
     * random name, lastname, and doctor_id.
     *
     * @return
     */
    public static Doctor randomDoctor() {
        // Construct a random identifier using Java's UUID class
        String id = UUID.randomUUID().toString();
        String name = "Name-"+id;
        String lastname = "Lastname-"+id;

        Doctor d = new Doctor(name, lastname);
        sDoctors.add(d);

        return d ;
    }

    /**
     * Construct and return a Patient object with a
     * random name, lastname, date_of_birth and medical_record_id.
     *
     * @return
     */
    public static Patient randomPatient() {
        Random rand = new Random();

        // Construct a random identifier using Java's UUID class
        String id = UUID.randomUUID().toString();
        String name = "Name-" + id;
        String lastname = "Lastname-" + id;
        //String date = "Date-" + id;
        long date = rand.nextLong() % 1400472045000L;

        Patient p = new Patient(name, lastname, date);
        //int random = (new Random()).nextInt(sDoctors.size());
        //p.setDoctor(sDoctors.get(random));
        sPatients.add(p);

        return p;
    }

    public static Medicine randomMedicine() {
        return new Medicine("Medicine-" + (new Random()).nextInt(100000));
    }

    public static CheckIn randomCheckIn() {
        return new CheckIn(randomDateMillis());
    }

    public static Question randomQuestion() {
        Random rand = new Random();

        String question = "Question-" + rand.nextInt(100000);
        String answer= "Answer-" + rand.nextInt(100000);

        return new Question(question, answer);
    }

    public static Set<Question> randomQuestionsSet() {
        Random rand = new Random();
        Set<Question> questions = new HashSet<Question>();

        for (int i = 0; i < (rand.nextInt(3) + 1); i++) {
            questions.add(randomQuestion());
        }

        return questions;
    }

    public static long randomDateMillis() {
        Random rand = new Random();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        int year = rand.nextInt(2010 - 1970 + 1) + 1970;
        int month = rand.nextInt(12) + 1;
        int day = rand.nextInt(28) + 1;
        // System.out.println("year = " + year);

        String dateStr = year + "-" + month + "-" + day;
        //System.out.println("String date: " + date);
        Date date = null;

        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("ParseException");
        }

        return date.getTime();
    }

    public static long randomDateAndTimeMillis() {
        Random rand = new Random();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int year = rand.nextInt(2010 - 1970 + 1) + 1970;
        int month = rand.nextInt(12) + 1;
        int day = rand.nextInt(28) + 1;
        int hour = rand.nextInt(24);
        int minute = rand.nextInt(60);
        int second = rand.nextInt(60);
        System.out.println("year = " + year);

        String date = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        //System.out.println("String date: " + date);
        Date datetime = null;

        try {
            datetime = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("ParseException");
        }

        return datetime.getTime();
    }

	/**
	 *  Convert an object to JSON using Jackson's ObjectMapper
	 *
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static String toJson(Object o) throws Exception{
	    return objectMapper.writeValueAsString(o);
	}
}
