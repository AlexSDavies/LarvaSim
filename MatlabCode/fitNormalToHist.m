function [mean,var] = fitNormalToHist(divs,counts)


	m = divs;
	f = counts;
	
	N = sum(f);
	
	mf = m.*f;
	m2f = m.*m.*f;
	
	Smf = sum(mf);
	Sm2f = sum(m2f);
	
	mean = Smf/N;
	var = (Sm2f - (Smf)^2/N)/N;

end