package com.example.ranunstrgen.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.TreeSet;
import java.util.regex.Matcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.ranunstrgen.Utils;
import com.example.ranunstrgen.model.StringTask;

@Service
public class StringTaskService {

	private static Logger logger = LogManager.getLogger(StringTaskService.class);

	@Value("${stringTaskService.uriReceiveNewJob}")
	private String uriReceiveNewJob;

	@Value("${stringTaskService.uriCheckRunningJobs}")
	private String uriCheckRunningJobs;

	@Value("${stringTaskService.uriGrabResults}")
	private String uriGrabResults;

	private final RestTemplate restTemplate;
	
	@Autowired
	public StringTaskService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Long receiveNewJob(StringTask task) throws FileNotFoundException, IOException {
		TreeSet<String> resultSet = new TreeSet<>();
		Long id = restTemplate.postForObject(uriReceiveNewJob, task, Long.class);
		Utils.findPermutations(task.getPossibleChars(), 0, task.getPossibleChars().length, resultSet);
		resultSet = Utils.findSubpermutations(task.getMinLength(), task.getMaxLength(), resultSet,
				task.getNumberOfStrings());
		saveToFile(id, resultSet);
		return id;
	}
	
	public ResponseEntity<Object> grabResults(Long id) throws FileNotFoundException {
		String path = System.getProperty("user.dir").replaceAll(Matcher.quoteReplacement("\\"), "/");
		File file = new File(path + "/task_" + id + "_.txt");
		String fileName = file.getName();
		StringTask task = restTemplate.getForObject(uriGrabResults + id.toString(), StringTask.class);
		if(!task.equals(null)) {
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			logger.info("The results from file " + fileName + " have been grabbed.");
			return ResponseEntity.status(HttpStatus.OK).contentLength(file.length()).body(resource);
		} else {
			logger.error("File named " + fileName + " was not found.");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("File not found.");
		}
	}
	
	public boolean areParametersCorrect(StringTask task) {
		boolean result;
        result = task.getMinLength() > 0;
        result &= task.getMaxLength() > 0;
        result &= task.getNumberOfStrings() > 0;
        result &= task.getPossibleChars().length != 0;
        result &= Utils.findNumberOfPermutations(task.getMinLength(), task.getMaxLength()) >= task.getNumberOfStrings();
        result &= task.getMinLength() != 0;
        result &= task.getMaxLength() != 0;
        result &= task.getNumberOfStrings() != 0;
        return result;
	}
	
	public void printJobRecevingMessage(StringTask task) {
        if(task.getMinLength() == 0 || task.getMaxLength() == 0 || task.getNumberOfStrings() == 0 || task.getPossibleChars() == null){
            logger.error("One of the required parameters has not been filled.");
        }
        if (task.getMinLength() < 0 || task.getMaxLength() < 0) {
            logger.error("The minimum or maximum length of string permutations cannot be less than 0.");
        } else if (task.getMinLength() > task.getMaxLength()) {
            logger.error("The minimum length of string permutation must be less than or equal to the maximum one.");
        } else if (task.getNumberOfStrings() <= 0) {
        	logger.error("The number of strings requested cannot be less than or equal to 0.");
        } else if  (task.getPossibleChars().length == 0) {
            logger.error("No characters were entered to make a strings.");
        } else if (Utils.findNumberOfPermutations(task.getMinLength(), task.getMaxLength()) < task.getNumberOfStrings()) {
        	logger.error("It is not possible to create as many permutations with the given characters.");
        } else {
            logger.info("String permutations creation is in progress...");
        }
    }
	
    public HttpStatus getHttpStatus(StringTask task) {
        if (areParametersCorrect(task)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

	public void saveToFile(Long id, TreeSet<String> stringSet) throws FileNotFoundException, IOException {
		String path = System.getProperty("user.dir").replaceAll(Matcher.quoteReplacement("\\"), "/");
		File file = new File(path + "/task_" + id + "_.txt");
		int counter = 0;
		if (file.createNewFile()) {
			FileOutputStream outputStream = new FileOutputStream(file, false);
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
			for (String str : stringSet) {
				counter++;
				bufferedWriter.write(str);
				if (counter < stringSet.size()) {
					bufferedWriter.newLine();
				}

			}
			bufferedWriter.close();
			outputStream.close();
		}
	}
}
