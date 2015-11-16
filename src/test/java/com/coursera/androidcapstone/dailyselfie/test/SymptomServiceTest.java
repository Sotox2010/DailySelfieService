package com.coursera.androidcapstone.dailyselfie.test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.FormUrlEncodedTypedOutput;

import com.coursera.androidcapstone.dailyselfie.client.DailySelfieService;
import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class SymptomServiceTest {

    private final String TEST_URL = "https://localhost:8443";
    //private final String TEST_URL = "http://localhost:8080";

    private Gson gson = new GsonBuilder().serializeNulls().create();

    private DailySelfieService service = new RestAdapter.Builder()
            //.setConverter(new GsonConverter(gson))
            .setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
            .setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
            .create(DailySelfieService.class);

    private final String AUTH_TOKEN_URL = TEST_URL + "/oauth/token";
    private final String USERNAME = "doctor1@email.com";
    private final String PASSWORD = "doctor1";
    private final String CLIENT_ID = "mobile";
    private final String CLIENT_SECRET = "";
    private final String GRANT_TYPE = "password";

    /*@Test
    public void testDoctorAddAndList() {

        for (int i = 0; i < 2; i++) {
            // Create random doctor.
            Doctor doctor = TestData.randomDoctor();

            // Send request to add the new patient and check if it added correctly
            Doctor received = service.addDoctor(doctor);
            assertEquals(doctor.getName(), received.getName());
            assertEquals(doctor.getLastname(), received.getLastname());
            assertEquals(doctor.getDoctorId(), received.getDoctorId());
        }

        // We should get back the doctor that we added above
        Collection<Doctor> doctors = service.getDoctorList();
        //assertTrue(doctors.contains(doctor));
    }*/

    /*@Test
    public void testPatientAddAndList() {

        for (int i = 0; i < 3; i++) {
            //Create random patient
            Patient patient = TestData.randomPatient();

            // Send request to add the new patient and check if it added correctly
            Patient received = service.addPatient(patient);
            assertEquals(patient.getName(), received.getName());
            assertEquals(patient.getLastname(), received.getLastname());
            assertEquals(patient.getDateOfBirth(), received.getDateOfBirth());
            assertEquals(patient.getMedicalRecordId(), received.getMedicalRecordId());
        }

        // Get list of all patients
        Collection<Patient> patients = service.getPatientList();
    }*/

    /*@Test
    public void testAddMedicine() {
        Medicine medicine = TestData.randomMedicine();
        service.addMedicineToPatient(5, medicine);
    }*/

    /*@Test
    public void testCheckIn() {
        //CheckIn checkInData = TestData.randomCheckIn();
        Set<Question> questions = TestData.randomQuestionsSet();
        //checkInData.setQuestions((Question[]) questions.toArray());

        Question[] result = service.checkIn(2, TestData.randomDateAndTimeMillis(), questions);
    }*/

    /*@Test
    public void testOAuth() {

        Request request;
        Client client = new OkClient();

        try {
            // Encode the username and password into the body of the request.
            FormUrlEncodedTypedOutput to = new FormUrlEncodedTypedOutput();
            to.addField("username", USERNAME);
            to.addField("password", PASSWORD);

            // Add the client ID and client secret to the body of the request.
            to.addField("client_id", CLIENT_ID);
            to.addField("client_secret", CLIENT_SECRET);

            // Indicate that we're using the OAuth Password Grant Flow
            // by adding grant_type=password to the body
            to.addField("grant_type", GRANT_TYPE);

            // The password grant requires BASIC authentication of the client.
            // In order to do BASIC authentication, we need to concatenate the
            // client_id and client_secret values together with a colon and then
            // Base64 encode them. The final value is added to the request as
            // the "Authorization" header and the value is set to "Basic " 
            // concatenated with the Base64 client_id:client_secret value described
            // above.
            String base64Auth = BaseEncoding.base64().encode(new String(CLIENT_ID + ":" + CLIENT_SECRET).getBytes());
            // Add the basic authorization header
            List<Header> headers = new ArrayList<Header>();
            headers.add(new Header("Authorization", "Basic " + base64Auth));

            // Create the actual password grant request using the data above
            request = new Request("POST", AUTH_TOKEN_URL, headers, to);

            // Request the password grant.
            Response response = client.execute(request);

            // Make sure the server responded with 200 OK
            if (response.getStatus() < 200 || response.getStatus() > 299) {
                // If not, we probably have bad credentials
                System.out.println("Login failure: " + response.getStatus() + " - " + response.getReason());
            } else {
                // Extract the string body from the response
                String body = IOUtils.toString(response.getBody().in());
                System.out.println(body);

                // Extract the access_token (bearer token) from the response so that we
                // can add it to future requests.
                //accessToken = new Gson().fromJson(body, JsonObject.class).get("access_token").getAsString();

                // Add the access_token to this request as the "Authorization"
                // header.
                //request.addHeader("Authorization", "Bearer " + accessToken);    

                // Let future calls know we've already fetched the access token
                //loggedIn = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void testOAuthRetrofit() {
        String base64Auth = BaseEncoding.base64().encode(new String(CLIENT_ID + ":" + CLIENT_SECRET).getBytes());
        // Add the basic authorization header
        String auth = "Basic " + base64Auth;

        String token = service.getOAuthToken(USERNAME, PASSWORD, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, auth);
    }
}
