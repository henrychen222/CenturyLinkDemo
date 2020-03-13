package com.codingChallenge.CenturyLinkDemo.Controller;

import com.codingChallenge.CenturyLinkDemo.Model.RepoInfo;
import com.codingChallenge.CenturyLinkDemo.Service.GitHubTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GitHubTrackController {

    @Autowired
    GitHubTrackService gitHubTrackService;

    @GetMapping("/getFollowers/{username}")
    public List<Map<String, String>> getFollowers(@PathVariable String username) throws Exception {
        return gitHubTrackService.retrieveFollowersByUsername(username);
    }

    @GetMapping("/getStargazers/{username}/{repo}")
    public List<Map<String, String>> getStargazers(@PathVariable String username, @PathVariable String repo) throws Exception {
        return gitHubTrackService.retrieveStargazersByUsernameRepoName(username, repo);
    }

    @GetMapping("/getRepoInfo/{username}")
    public List<RepoInfo> getRepoInfo(@PathVariable String username) throws Exception {
        return gitHubTrackService.retrieveReposByUsername(username);
    }


}