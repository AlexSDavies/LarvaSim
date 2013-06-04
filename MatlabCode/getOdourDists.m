function props = getOdourDists(simStats)


	pos = simStats.data.midPos;
	dists = sqrt(sum(pos.^2,2));

	counts = histc(dists,[0:50:600]);
	
	props = counts./sum(counts);