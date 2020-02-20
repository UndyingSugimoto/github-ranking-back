package github_ranking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github_ranking.data.UserInformationRes;
import github_ranking.service.UserInformationService;

@RestController
@RequestMapping(value = "github-ranking")
public class GitHubRankingController {

	@Autowired
	private UserInformationService userInformationService;

	@PostMapping("userentry")
	@CrossOrigin(origins = {"http://localhost:3000"})
	public void entryUserInfomation(@RequestBody UserInformationRes res) {
		System.out.println(res);
		userInformationService.entryUserInformation(res);

	}
}
