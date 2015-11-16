package com.coursera.androidcapstone.dailyselfie.client;

import java.util.Collection;

import com.coursera.androidcapstone.dailyselfie.model.Doctor;
import com.coursera.androidcapstone.dailyselfie.model.Medicine;
import com.coursera.androidcapstone.dailyselfie.model.Patient;
import com.coursera.androidcapstone.dailyselfie.model.Question;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * This interface defines an API for a VideoSvc. The
 * interface is used to provide a contract for client/server
 * interactions. The interface is annotated with Retrofit
 * annotations so that clients can automatically convert the
 *
 *
 * @author jules
 *
 */
public interface DailySelfieService {

    public static final String DOCTOR_ID_PARAMETER = "doctor_id";
    public static final String PATIENT_ID_PARAMETER = "patient_id";
    public static final String CHECK_IN_ID_PARAMETER = "check_in_id";
    public static final String TIME_MILLIS_PARAMETER = "timemillis";

    public static final String DOCTOR_PATH = "/doctor";
    public static final String DOCTOR_ID_SEARCH_PATH = DOCTOR_PATH + "/find";

    public static final String PATIENT_PATH = "/patient";
    public static final String PATIENT_BY_DOCTOR_ID_SEARCH_PATH = PATIENT_PATH + "/find";

    public static final String MEDICINE_PATH = "/medicine";
    public static final String MEDICINE_ID_PATH = "medicine_id";
    public static final String MEDICINE_ADD_PATH = MEDICINE_PATH + "/add";
    public static final String MEDICINE_REMOVE_PATH = MEDICINE_PATH + "/remove/{" + MEDICINE_ID_PATH + "}";

    public static final String CHECK_IN_PATH = "/check_in";

    public static final String QUESTION_PATH = "/question";


    @GET(DOCTOR_PATH)
    public Collection<Doctor> getDoctorList();

    @POST(DOCTOR_PATH)
    public Doctor addDoctor(@Body Doctor doctor);

    /*@GET(DOCTOR_ID_SEARCH_PATH)
    public Doctor findByDoctorId(@Query(DOCTOR_ID_PARAMETER) String doctor_id);*/


    @GET(PATIENT_PATH)
    public Collection<Patient> getPatientList();

    @POST(PATIENT_PATH)
    public Patient addPatient(@Body Patient patient);

    @POST(PATIENT_BY_DOCTOR_ID_SEARCH_PATH)
    public Collection<Patient> findPatientsByDoctorId(@Query(DOCTOR_ID_PARAMETER) long id);

    @POST(MEDICINE_ADD_PATH)
    public boolean addMedicineToPatient(
            @Query(PATIENT_ID_PARAMETER) long patientId,
            @Body Medicine medicine);

    @POST(MEDICINE_REMOVE_PATH)
    public boolean removeMedicineForPatient(
            @Path(MEDICINE_ID_PATH) long medicineId);

    @POST(CHECK_IN_PATH)
    public Question[] checkIn(
            @Query(PATIENT_ID_PARAMETER) long patientId,
            @Query(TIME_MILLIS_PARAMETER) long time_millis,
            @Body Collection<Question> questions);

    @POST(QUESTION_PATH)
    public Question addQuestionToCheckIn(
        @Query(CHECK_IN_ID_PARAMETER) long checkInId,
        @Body Question question);

    @FormUrlEncoded
    @POST("/oauth/token")
    public String getOAuthToken(
            @Field("username") String userName,
            @Field("password") String password,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType,
            @Header("Authorization") String authorization);
}
