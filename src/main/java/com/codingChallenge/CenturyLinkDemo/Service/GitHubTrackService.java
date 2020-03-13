package com.codingChallenge.CenturyLinkDemo.Service;

import com.codingChallenge.CenturyLinkDemo.Helper.GitHubTrackHelper;
import com.codingChallenge.CenturyLinkDemo.Model.RepoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GitHubTrackService {

    @Autowired
    private GitHubTrackHelper gitHubTrackHelper;

    public List<Map<String, String>> retrieveFollowersByUsername(String username) throws Exception {
        return gitHubTrackHelper.retrieveFollowersByUsernameOperation(username);
    }

    public List<Map<String, String>> retrieveStargazersByUsernameRepoName(String username, String repo) throws Exception {
        return gitHubTrackHelper.retrieveStargazersByUsernameRepoNameOperation(username, repo);
    }

    public List<RepoInfo> retrieveReposByUsername(String username) throws Exception {
        return gitHubTrackHelper.retrieveReposByUsernameOperation(username);
    }

}
