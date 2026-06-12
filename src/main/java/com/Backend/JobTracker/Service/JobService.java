package com.Backend.JobTracker.Service;

import com.Backend.JobTracker.DTO.JobDTO;
import com.Backend.JobTracker.Entity.Jobs;
import com.Backend.JobTracker.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.Backend.JobTracker.Repository.JobRepo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    @Autowired
    private JobRepo jobRepo;

    //Fetching all jobs...
    public ResponseEntity<List<Jobs>> getAllJobs(){
        return ResponseEntity.ok(jobRepo.findAll());
    }
    //Creating a new jobs with resume file..!!
    public ResponseEntity<Jobs> saveJob(Jobs job){

        return new ResponseEntity<>(jobRepo.save(job) , HttpStatus.CREATED);
    }
   //Fetching a single job by there ID...
    public JobDTO findByID(Long id){
          return jobRepo.getAllJobsById(id);
//        return jobRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Job found with this id..:"+id));
    }
    //Updating jobs by Using there Ids...!!
    public ResponseEntity<Jobs> updateJobById(Long id , Jobs job , MultipartFile Resume){

       Jobs editJob = jobRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Job found with this id..:"+id));
       editJob.setCompanyName(job.getCompanyName());
       editJob.setRole(job.getRole());
       editJob.setApplyDate(job.getApplyDate());
       editJob.setStatus(job.getStatus());
       editJob.setNotes(job.getNotes());
//       editJob.setResumeUrl(job.getResumeUrl());

       if(Resume != null && !Resume.isEmpty()){
               try {
                   editJob.setResumeUrl(Resume.getOriginalFilename());
                   editJob.setPdfBytes(Resume.getBytes());
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
       }
       jobRepo.save(editJob);
       return ResponseEntity.ok(editJob);
    }
    //Delete job by there Id...!!
    public ResponseEntity<?> deleteJobById(Long id){
        Jobs deleteJob = jobRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Job found with this id..:"+id));
        jobRepo.deleteById(deleteJob.getId());
        return ResponseEntity.ok("The record is deleted including Resume bytes file also..!!");
    }

    public ResponseEntity<Jobs> updatingExitingStatusById(Long id, Jobs job) {
        Jobs editJobStatus = jobRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Job found with this id..:"+id));
        editJobStatus.setStatus(job.getStatus());
        return ResponseEntity.ok(jobRepo.save(editJobStatus));
    }

    public ResponseEntity<?> resumeUrlDel(Long id, String fileName) {
        Jobs hasToDelResumePath = jobRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Job found with this id..:"+id));
        if( hasToDelResumePath.getResumeUrl().equals(fileName)) {
            hasToDelResumePath.setResumeUrl(null);
            hasToDelResumePath.setPdfBytes(null);
        }
        return ResponseEntity.ok(jobRepo.save(hasToDelResumePath));
    }

    public List<JobDTO> getAllJobsWithByteData(){
        return jobRepo.getAllJobs();
    }

    public ResponseEntity<byte[]> getResumeByFileName(String fileName) {
      // jobRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Job found with this id..:"+id));
        Jobs job = jobRepo.findByResumeUrl(fileName);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).header(
                HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"" + fileName + "\""
        ).body(job.getPdfBytes());
    }

    public ResponseEntity<byte[]> downloadResume(String fileName) {
        Jobs job = jobRepo.findByResumeUrl(fileName);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileName + "\""
        ).body(job.getPdfBytes());
    }
}
