package com.test.hostName;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Objects;

@Controller
public class HomeController {

    @GetMapping
    @ResponseBody
    public String home() throws IOException {
        String gitRepo = "https://github.com/monayem02/devops-task-1.git";
        String hash = getLastCommitHashByPublicRepo(gitRepo);

        String hostHostname = System.getenv("HOST_HOSTNAME");
        String hostName = Objects.nonNull(hostHostname) ? hostHostname : InetAddress.getLocalHost().getHostName();
        return "<b>Host Name: </b>" + hostName + "<br>" + "<b>Last commit Hash: </b>" + hash;
    }

    private static String getLastCommitHashByPublicRepo(String gitRepo) throws IOException {
        ProcessBuilder builder = new ProcessBuilder("git", "ls-remote", gitRepo, "refs/heads/main");
        Process process = builder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();
        String hash;
        if (line != null && !line.isEmpty()) {
            String[] parts = line.split("\\s+");
            hash = parts[0];
        } else {
            hash = "No output from git ls-remote.";
        }
        return hash;
    }
}
