package com.codingChallenge.CenturyLinkDemo.Helper;

import com.codingChallenge.CenturyLinkDemo.Model.Owner;
import com.codingChallenge.CenturyLinkDemo.Model.RepoInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class GitHubTrackHelper {

    private static final Logger logger = Logger.getLogger(GitHubTrackHelper.class.getName());

    public List<Map<String, String>> retrieveFollowersByUsernameOperation(String username) throws Exception {
        // String username = "henrychen222";
        String link = "https://api.github.com/users/" + username + "/followers";
        URL url1 = new URL(link);
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();

        JSONArray jsonArray = getJSONArrayData(conn);
        return getStargazersOrFollowersData(jsonArray);
    }

    public List<Map<String, String>> retrieveStargazersByUsernameRepoNameOperation(String username, String repo) throws Exception {
//        String username = "996icu";
//        String repo = "996.ICU";
        String link = "https://api.github.com/repos/" + username + "/" + repo + "/stargazers";
        HttpURLConnection conn = (HttpURLConnection) new URL(link).openConnection();

        JSONArray jsonArray = getJSONArrayData(conn);
        System.out.println(jsonArray);
        logger.log(Level.FINER, "Stargazers jsonArray", jsonArray);
        return getStargazersOrFollowersData(jsonArray);
    }

    public List<Map<String, String>> getStargazersOrFollowersData(JSONArray jsonArray) {
        List<Map<String, String>> list = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i); // get each item
                String login = item.getString("login");
                int id = item.getInt("id");
                String node_id = item.getString("node_id");
                String avatar_url = item.getString("avatar_url");
                String gravatar_id = item.getString("gravatar_id");
                String url = item.getString("url");
                String html_url = item.getString("html_url");
                String followers_url = item.getString("followers_url");
                String following_url = item.getString("following_url");
                String gists_url = item.getString("gists_url");
                String starred_url = item.getString("starred_url");
                String subscriptions_url = item.getString("subscriptions_url");
                String organizations_url = item.getString("organizations_url");
                String repos_url = item.getString("repos_url");
                String events_url = item.getString("events_url");
                String received_events_url = item.getString("received_events_url");
                String type = item.getString("type");
                boolean site_admin = item.getBoolean("site_admin");


                // every time use a new map for the object,
                // if define global, gonna be overwrite, will show repeated object of the last object
                Map map = new HashMap<String, String>();
                map.put("login", login);
                map.put("id", id + ""); // convert to string
                map.put("node_id", node_id);
                map.put("avatar_url", avatar_url);
                map.put("gravatar_id", gravatar_id);
                map.put("url", url);
                map.put("html_url", html_url);
                map.put("followers_url", followers_url);
                map.put("following_url", following_url);
                map.put("gists_url", gists_url);
                map.put("subscriptions_url", subscriptions_url);
                map.put("organizations_url", organizations_url);
                map.put("repos_url", repos_url);
                map.put("starred_url", starred_url);
                map.put("events_url", events_url);
                map.put("received_events_url", received_events_url);
                map.put("type", type);
                map.put("site_admin", site_admin + "");

                list.add(map);
            }
        }
        return list;
    }

    public List<RepoInfo> retrieveReposByUsernameOperation(String username) throws Exception {
        String link = "https://api.github.com/users/" + username + "/repos";
        HttpURLConnection conn = (HttpURLConnection) new URL(link).openConnection();

        JSONArray jsonArray = getJSONArrayData(conn);
        System.out.println(jsonArray);
        return getRepoInfoData(jsonArray);
    }

    public List<RepoInfo> getRepoInfoData(JSONArray jsonArray) {
        List<RepoInfo> list = new ArrayList<>();

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                RepoInfo repoInfo = new RepoInfo();

                repoInfo.setId(item.getInt("id"));
                repoInfo.setNode_id(item.getString("node_id"));
                repoInfo.setName(item.getString("name"));
                repoInfo.setFull_name(item.getString("full_name"));
//                repoInfo.setPrivate(item.getString("Private")); // Private

                // deal with owner
                JSONObject nestedItem = item.getJSONObject("owner");
                Owner owner = new Owner();
                owner.setLogin(nestedItem.getString("login"));
                owner.setId(nestedItem.getInt("id"));
                owner.setNode_id(nestedItem.getString("node_id"));
                owner.setAvatar_url(nestedItem.getString("avatar_url"));
                owner.setGravatar_id(nestedItem.getString("gravatar_id"));
                owner.setUrl(nestedItem.getString("url"));
                owner.setHtml_url(nestedItem.getString("html_url"));
                owner.setFollowers_url(nestedItem.getString("followers_url"));
                owner.setFollowing_url(nestedItem.getString("following_url"));
                owner.setGists_url(nestedItem.getString("gists_url"));
                owner.setStarred_url(nestedItem.getString("starred_url"));
                owner.setSubscriptions_url(nestedItem.getString("subscriptions_url"));
                owner.setOrganizations_url(nestedItem.getString("organizations_url"));
                owner.setRepos_url(nestedItem.getString("repos_url"));
                owner.setEvents_url(nestedItem.getString("events_url"));
                owner.setReceived_events_url(nestedItem.getString("received_events_url"));
                owner.setType(nestedItem.getString("type"));
                owner.setSite_admin(nestedItem.getBoolean("site_admin"));
                repoInfo.setOwner(owner);

                repoInfo.setHtml_url(item.getString("html_url"));
//               repoInfo.setDescription(item.getString("description"));
                repoInfo.setFork(item.getBoolean("fork"));
                repoInfo.setUrl(item.getString("url"));
                repoInfo.setForks_url(item.getString("forks_url"));
                repoInfo.setKeys_url(item.getString("keys_url"));
                repoInfo.setCollaborators_url(item.getString("collaborators_url"));
                repoInfo.setTeams_url(item.getString("teams_url"));
                repoInfo.setHooks_url(item.getString("hooks_url"));
                repoInfo.setIssue_events_url(item.getString("issue_events_url"));
                repoInfo.setEvents_url(item.getString("events_url"));
                repoInfo.setAssignees_url(item.getString("assignees_url"));
                repoInfo.setBranches_url(item.getString("branches_url"));
                repoInfo.setTags_url(item.getString("tags_url"));
                repoInfo.setBlobs_url(item.getString("blobs_url"));
                repoInfo.setGit_tags_url(item.getString("git_tags_url"));
                repoInfo.setGit_refs_url(item.getString("git_refs_url"));
                repoInfo.setTrees_url(item.getString("trees_url"));
                repoInfo.setStatuses_url(item.getString("statuses_url"));
                repoInfo.setLabels_url(item.getString("languages_url"));
                repoInfo.setStargazers_url(item.getString("stargazers_url"));
                repoInfo.setContributors_url(item.getString("contributors_url"));
                repoInfo.setSubscribers_url(item.getString("subscribers_url"));
                repoInfo.setSubscription_url(item.getString("subscription_url"));
                repoInfo.setComments_url(item.getString("commits_url"));
                repoInfo.setGit_commits_url(item.getString("git_commits_url"));
                repoInfo.setComments_url(item.getString("comments_url"));
                repoInfo.setIssue_comment_url(item.getString("issue_comment_url"));
                repoInfo.setContents_url(item.getString("contents_url"));
                repoInfo.setCompare_url(item.getString("compare_url"));
                repoInfo.setMerges_url(item.getString("merges_url"));
                repoInfo.setArchive_url(item.getString("archive_url"));
                repoInfo.setDownloads_url(item.getString("downloads_url"));
                repoInfo.setIssues_url(item.getString("issues_url"));
                repoInfo.setPulls_url(item.getString("pulls_url"));
                repoInfo.setMilestones_url(item.getString("milestones_url"));
                repoInfo.setNotifications_url(item.getString("notifications_url"));
                repoInfo.setLabels_url(item.getString("labels_url"));
                repoInfo.setReleases_url(item.getString("releases_url"));
                repoInfo.setDeployments_url(item.getString("deployments_url"));
                repoInfo.setCreated_at(item.getString("created_at"));
                repoInfo.setUpdated_at(item.getString("updated_at"));
                repoInfo.setPushed_at(item.getString("pushed_at"));
                repoInfo.setGit_url(item.getString("git_url"));
                repoInfo.setSsh_url(item.getString("ssh_url"));
                repoInfo.setClone_url(item.getString("clone_url"));
                repoInfo.setSvn_url(item.getString("svn_url"));
//                repoInfo.setHomepage(item.getString("homepage"));
                repoInfo.setSize(item.getInt("size"));
                repoInfo.setStargazers_count(item.getInt("stargazers_count"));
                repoInfo.setWatchers_count(item.getInt("watchers_count"));
//                repoInfo.setLanguage(item.getString("language"));
                repoInfo.setHas_issues(item.getBoolean("has_issues"));
                repoInfo.setHas_projects(item.getBoolean("has_projects"));
                repoInfo.setHas_downloads(item.getBoolean("has_downloads"));
                repoInfo.setHas_wiki(item.getBoolean("has_wiki"));
                repoInfo.setHas_pages(item.getBoolean("has_pages"));
                repoInfo.setForks_count(item.getInt("forks_count"));
//              repoInfo.setMirror_url(item.getString("mirror_url"));
                repoInfo.setArchived(item.getBoolean("archived"));
                repoInfo.setDisabled(item.getBoolean("disabled"));
                repoInfo.setOpen_issues_count(item.getInt("open_issues_count"));
//              repoInfo.setLicense(item.getString("license"));
                repoInfo.setForks(item.getInt("forks"));
                repoInfo.setOpen_issues(item.getInt("open_issues"));
                repoInfo.setWatchers(item.getInt("watchers"));
                repoInfo.setDefault_branch(item.getString("default_branch"));

                list.add(repoInfo);
            }
        }
        return list;
    }

    public JSONArray getJSONArrayData(HttpURLConnection conn) throws Exception {
        conn.setConnectTimeout(15 * 1000);
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);

        InputStream is = conn.getInputStream();
        byte[] data = readStream(is);
        String jsonArrayString = new String(data);

        JSONArray jsonArray = new JSONArray(jsonArrayString);
        return jsonArray;

    }

    private static byte[] readStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            bout.write(buffer, 0, len);
        }
        bout.close();
        inputStream.close();
        return bout.toByteArray();
    }


}