package github_ranking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import github_ranking.data.UserInformationRes;
import github_ranking.data.res.RanksByLanguageRes;
import github_ranking.data.res.UserDetailRes;
import github_ranking.data.res.UserEntryRes;
import github_ranking.data.res.UserExistsRes;
import github_ranking.service.UserInformationService;

@RestController
@RequestMapping(value = "github-ranking")
public class GitHubRankingController {

	@Autowired
	private UserInformationService userInformationService;

	@PostMapping("user/entry")
	@CrossOrigin(origins = {"http://localhost:3000", "https://github-ranking.herokuapp.com"})
	public UserEntryRes entryUserInfomation(@RequestBody UserInformationRes res) {
		return userInformationService.entryUserInformation(res);

	}

	@PostMapping("user/update")
	@CrossOrigin(origins = {"http://localhost:3000", "https://github-ranking.herokuapp.com"})
	public UserEntryRes updateUserInfomation(@RequestBody UserInformationRes res) {
		return userInformationService.updateUserInformation(res);

	}

	@GetMapping("user/exists")
	@CrossOrigin(origins = {"http://localhost:3000", "https://github-ranking.herokuapp.com"})
	public UserExistsRes userExists(@RequestParam("userId") String userId) {
		UserExistsRes res = userInformationService.userExists(userId);
		return res;
	}

	@GetMapping("user/detail")
	@CrossOrigin(origins = {"http://localhost:3000", "https://github-ranking.herokuapp.com"})
	public UserDetailRes getUserDetail(@RequestParam("userId") String userId) {
		return userInformationService.getUserDetail(userId);
	}

	@GetMapping("ranking")
	@CrossOrigin(origins = {"http://localhost:3000", "https://github-ranking.herokuapp.com"})
	public RanksByLanguageRes getRanking() {
		return userInformationService.getRanksByLanguage();
	}

	@GetMapping("test")
	@CrossOrigin(origins = {"http://localhost:3000", "https://github-ranking.herokuapp.com"})
	public UserDetailRes test() {
		UserDetailRes res = new UserDetailRes();
		res.setUserId("test");
		return res;
	}
	@GetMapping("calc")
	@CrossOrigin(origins = {"http://localhost:3000", "https://github-ranking.herokuapp.com"})
	public Integer plus(@RequestParam("one") Integer one,@RequestParam("two") Integer two) {
		Integer res = one + two;
		return res;
	}
}
