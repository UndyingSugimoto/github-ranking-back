package github_ranking.data.res;

import java.util.List;

import github_ranking.data.RankByLanguage;
import lombok.Data;

@Data
public class RanksByLanguageRes {
	private List<RankByLanguage> rankByLanguages;
}
