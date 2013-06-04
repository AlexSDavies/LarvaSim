function d = pathDistToOdour(simStats,thresh)

	pos = simStats.data.midPos;
	
	dists = zeros(length(pos),1);
	
	for i = 2:length(pos)
		
		moveDist = sqrt(sum((pos(i,:) - pos(i-1,:)).^2));
		dists(i) = moveDist;
		 
		if(sqrt(sum(pos(i,:).^2)) < thresh)
			break
		end
		
	end
		
	d = sum(dists);
		