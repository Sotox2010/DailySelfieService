package com.coursera.androidcapstone.dailyselfie.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.coursera.androidcapstone.dailyselfie.client.DailySelfieService;
import com.coursera.androidcapstone.dailyselfie.model.CheckIn;
import com.coursera.androidcapstone.dailyselfie.model.Doctor;
import com.coursera.androidcapstone.dailyselfie.model.Medicine;
import com.coursera.androidcapstone.dailyselfie.model.Patient;
import com.coursera.androidcapstone.dailyselfie.model.Photo;
import com.coursera.androidcapstone.dailyselfie.model.Question;
import com.coursera.androidcapstone.dailyselfie.model.User;
import com.coursera.androidcapstone.dailyselfie.repository.CheckInRepository;
import com.coursera.androidcapstone.dailyselfie.repository.DoctorRepository;
import com.coursera.androidcapstone.dailyselfie.repository.MedicineRepository;
import com.coursera.androidcapstone.dailyselfie.repository.PatientRepository;
import com.coursera.androidcapstone.dailyselfie.repository.PhotoRepository;
import com.coursera.androidcapstone.dailyselfie.repository.QuestionRepository;
import com.coursera.androidcapstone.dailyselfie.repository.UserRepository;
import com.google.common.collect.Lists;

import magick.ImageInfo;
import magick.ImageMagick;
import magick.MagickException;
import magick.MagickImage;

@Controller
public class MainController {

    @Autowired
    private DoctorRepository doctors;

    @Autowired
    private PatientRepository patients;

    @Autowired
    private MedicineRepository medicines;

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private UserRepository users;

    @Autowired
    private PhotoRepository photos;


    /*@RequestMapping(value="/doctor_random", method=RequestMethod.POST)
    public @ResponseBody boolean addRandomDoctor() {
        doctors.save(TestData.randomDoctor());
        return true;
    }*/

    @RequestMapping(value=DailySelfieService.DOCTOR_PATH, method=RequestMethod.POST)
    public @ResponseBody Doctor addDoctor(@RequestBody Doctor doctor) {
        return doctors.save(doctor);
    }

    @RequestMapping(value=DailySelfieService.DOCTOR_PATH, method=RequestMethod.GET)
    public @ResponseBody Collection<Doctor> getDoctorList() {
        return Lists.newArrayList(doctors.findAll());
    }

    /*@RequestMapping(value="/patient_random", method=RequestMethod.POST)
    public @ResponseBody boolean addRandomPatient() {
        return patients.save(TestData.randomPatient()) != null;
    }*/

    @RequestMapping(value=DailySelfieService.PATIENT_PATH, method=RequestMethod.POST)
    public @ResponseBody Patient addPatient(@RequestBody Patient p) {
        return patients.save(p);
    }

    @RequestMapping(value=DailySelfieService.PATIENT_PATH, method=RequestMethod.GET)
    public @ResponseBody Collection<Patient> getPatientList() {
        return Lists.newArrayList(patients.findAll());
    }

    @RequestMapping(value=DailySelfieService.PATIENT_PATH + "/find_patient_by_email", method=RequestMethod.POST)
    public @ResponseBody Patient findPatientByEmail(@RequestParam("email") String email) {
        return patients.findByEmail(email);
    }


    @RequestMapping(value=DailySelfieService.PATIENT_BY_DOCTOR_ID_SEARCH_PATH, method=RequestMethod.POST)
    public @ResponseBody Collection<Patient> findPatientsByDoctorEmail(@RequestParam("email") String email) {
        Doctor d = doctors.findByEmail(email);
        return patients.findByDoctor(d);
    }

    @RequestMapping(value=DailySelfieService.MEDICINE_ADD_PATH, method=RequestMethod.POST)
    public @ResponseBody boolean addMadicineToPatient(
            @RequestParam(DailySelfieService.PATIENT_ID_PARAMETER) long patientId,
            @RequestBody Medicine medicine) {

        Patient p = patients.findOne(patientId);
        medicine.setPatient(p);

        return medicines.save(medicine) != null;
    }

    @RequestMapping(value=DailySelfieService.MEDICINE_REMOVE_PATH, method=RequestMethod.POST)
    public @ResponseBody boolean addMadicineToPatient(
            @PathVariable(DailySelfieService.MEDICINE_ID_PATH) long medicineId,
            HttpServletResponse response) {

        if (medicines.exists(medicineId)) {
            medicines.delete(medicineId);
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return false;
    }

    @RequestMapping(value=DailySelfieService.CHECK_IN_PATH, method=RequestMethod.GET)
    public @ResponseBody Collection<CheckIn> getCheckInList() {
        return Lists.newArrayList(checkInRepository.findAll());
    }

    @RequestMapping(value=DailySelfieService.CHECK_IN_PATH, method=RequestMethod.POST)
    public @ResponseBody boolean checkIn(
            @RequestParam(DailySelfieService.PATIENT_ID_PARAMETER) long patientId,
            @RequestParam(DailySelfieService.TIME_MILLIS_PARAMETER) long timemillis,
            @RequestBody Question[] qlist) {

        Patient p = patients.findOne(patientId);
        CheckIn c = new CheckIn(timemillis);
        c.setPatient(p);
        checkInRepository.save(c);

        for (Question q : qlist) {
            q.setCheckIn(c);
            questions.save(q);
        }

        return true;
    }

    @RequestMapping(value=DailySelfieService.QUESTION_PATH, method=RequestMethod.GET)
    public @ResponseBody Collection<Question> getQuestionList() {
        return Lists.newArrayList(questions.findAll());
    }

    @RequestMapping(value=DailySelfieService.QUESTION_PATH, method=RequestMethod.POST)
    public @ResponseBody Question addQuestionToCheckIn(
            @RequestParam(DailySelfieService.CHECK_IN_ID_PARAMETER) long checkInDataId,
            @RequestBody Question question) {

        System.out.print(question.toString());
        CheckIn c = checkInRepository.findOne(checkInDataId);
        question.setCheckIn(c);
        return questions.save(question);
    }

    @RequestMapping(value="/check_user_type", method=RequestMethod.POST)
    @ResponseBody Boolean checkUserType(
            @RequestParam("username") String username,
            HttpServletResponse response) {

        Doctor doctor = doctors.findByEmail(username);
        Patient patient = patients.findByEmail(username);

        if (doctor != null) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        } else if (patient != null) {
            response.setStatus(HttpServletResponse.SC_OK);
            return false;
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return null;
    }

    @PreAuthorize("hasRole('ROLE_TRUSTED_CLIENT')")
    @RequestMapping(value="/photo/{name.+}", method=RequestMethod.GET)
    public void downloadPhoto(
            @PathVariable("name.+") String name,
            HttpServletResponse response,
            Principal principal) {

        User owner = (User) ((Authentication) principal).getPrincipal();
        Photo photo = photos.findByNameAndOwner(name, owner);

        System.out.println("Principal name: " + owner.getUsername());

        if (photo != null) {
            File photoToDownload = new File(photo.getUri());
            if (!photoToDownload.exists()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            try {
                response.setContentType("image/jpg");
                response.setContentLength((int) photoToDownload.length());
                IOUtils.copy(FileUtils.openInputStream(photoToDownload), response.getOutputStream());
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_TRUSTED_CLIENT')")
    @RequestMapping(value="/photo", method=RequestMethod.POST)
    public @ResponseBody Boolean uploadPhoto(
            @RequestParam("name") String name,
            @RequestParam("photo") MultipartFile photoFile,
            Principal principal) {

        File dir = new File("resources" + File.separator + "users" + File.separator + principal.getName() + File.separator + "photos");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            File newPhotoFile = new File(dir.getAbsolutePath() + File.separator + name);
            FileUtils.copyInputStreamToFile(photoFile.getInputStream(), newPhotoFile);

            File convertedPhotoFile = convertToPng(newPhotoFile);

            User owner = (User) ((Authentication) principal).getPrincipal();
            Photo newPhoto = new Photo(name, newPhotoFile.getPath(), owner);
            photos.save(newPhoto);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private File convertToPng(File photoFile) {
        try {
            ImageInfo info = new ImageInfo(photoFile.getPath());
            MagickImage magick = new MagickImage(info);
            System.out.println("MAGICK: " + magick.getMagick());

            if (magick.getMagick() == "PNG") {
                return photoFile;
            }

            String path = photoFile.getPath().substring(0, photoFile.getPath().lastIndexOf(File.separator));
            String name = photoFile.getName().substring(0, photoFile.getName().lastIndexOf('.')) + ".png";
            magick.setFileName(name);
            magick.writeImage(info);

            photoFile.delete();
            return new File(path + File.separator + name);

        } catch (MagickException e) {
            e.printStackTrace();
            return photoFile;
        }
    }
}
