package com.Backend.JobTracker.Controller;

import com.Backend.JobTracker.DTO.JobDTO;
import com.Backend.JobTracker.Entity.Jobs;
import com.Backend.JobTracker.Repository.JobRepo;
import com.Backend.JobTracker.Service.JobService;
import com.Backend.JobTracker.Service.JobStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class JobController {
    @Autowired
    public JobService jobService;

    @Autowired
    public JobStatus jobStatusService;

    //View end Point..!!

    @GetMapping("resume/view/{filename}")
    public ResponseEntity<byte[]> viewResume(@PathVariable String filename){
        return jobService.getResumeByFileName(filename);
    }

    //Download end Point..!!
    @GetMapping("resume/download/{fileName}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable String fileName){
       return jobService.downloadResume(fileName);
    }

//    @GetMapping("/Jobs")
//    public ResponseEntity<List<Jobs>> getAllJobs(){
//        return jobService.getAllJobs();
//    }

    @GetMapping("/status")
    public ResponseEntity<Map<String ,Object>> getAllStatus(){
       return ResponseEntity.ok(jobStatusService.status());
   }

    @PatchMapping("Jobs/{id}")
    public ResponseEntity<Jobs> updatingExitingStatusById(@PathVariable Long id , @RequestBody Jobs job){
       return jobService.updatingExitingStatusById(id,job);
   }

    //handling Resume deletion...through fileName

    @PatchMapping("DelResumeUrl/{id}")
    public ResponseEntity<?> resumeUrlDel(@PathVariable Long id , @RequestParam String fileName){
        return jobService.resumeUrlDel(id ,fileName);
    }
    //Handling edit file in this method..!!
    @PutMapping("/Jobs/{id}")
    public ResponseEntity<Jobs> updateById(@PathVariable Long id,
                           @RequestParam String CompanyName ,
                           @RequestParam String Role ,
                           @RequestParam LocalDate ApplyDate ,
                           @RequestParam String Status,
                           @RequestParam String Notes ,
                           @RequestParam(required = false) MultipartFile Resume
                          ){
        Jobs job = new Jobs();
        job.setCompanyName(CompanyName);
        job.setRole(Role);
        job.setApplyDate(ApplyDate);
        job.setStatus(Status);
        job.setNotes(Notes);
        if(Resume != null && !Resume.isEmpty()) {
            job.setResumeUrl(Resume.getOriginalFilename());
        }
        System.out.println(id);
        System.out.println(job);
        return  jobService.updateJobById(id , job , Resume);
   }

   //Handling file post in this method!!
    @PostMapping("/Jobs")
    public ResponseEntity<Jobs> saveJob(@RequestParam String CompanyName ,
                                          @RequestParam String Role ,
                                          @RequestParam LocalDate ApplyDate ,
                                          @RequestParam String Status,
                                          @RequestParam String Notes ,
                                          @RequestParam(required = false) MultipartFile Resume){
        Jobs job = new Jobs();
        job.setCompanyName(CompanyName);
        job.setRole(Role);
        job.setApplyDate(ApplyDate);
        job.setStatus(Status);
        job.setNotes(Notes);
        if(Resume != null && !Resume.isEmpty()){
            job.setResumeUrl(Resume.getOriginalFilename());
            try {
                job.setPdfBytes(Resume.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return jobService.saveJob(job);
    }

    @DeleteMapping("/Jobs/{id}")
    public ResponseEntity<?> deleteJobById(@PathVariable Long id){
        return jobService.deleteJobById(id);
    }

    @GetMapping("/JobsDTO")
    public List<JobDTO> getAllJobsWithByteData(){
        return jobService.getAllJobsWithByteData();
    }

    @GetMapping("/JobsDTO/{id}")
    public ResponseEntity<JobDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(jobService.findByID(id));
    }

}
