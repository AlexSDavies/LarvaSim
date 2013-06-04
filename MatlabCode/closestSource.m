function numSources = closestSource

for sd = 1:4
	for larvaNum = 1:10
	
		name = ['multiSourceSD_' num2str(sd) '_Larva_' num2str(larvaNum)];
		simStats(sd,larvaNum) = getSimStats(name, 0.1);
		
		disp(['File ' num2str(sd) ' ' num2str(larvaNum)]);
		
	end
end


for sd = 1:4
	for larvaNum = 1:10
	
		pos = simStats(sd,larvaNum).data.midPos;
		
		sources = [0 0 0];
		for i = 1:length(pos)
			dist1 = sum((pos(i,:) - [0 34]).^2);
			dist2 = sum((pos(i,:) - [-30 -16]).^2);
			dist3 = sum((pos(i,:) - [30 -16]).^2);
			
			if(dist1 < dist2 && dist1 < dist3)
				sources(1) = sources(1) + 1;
			end
			
			if(dist2 < dist1 && dist2 < dist3)
				sources(2) = sources(2) + 1;
			end
			
			if(dist3 < dist2 && dist3 < dist1)
				sources(3) = sources(3) + 1;
			end
			
		end
		
		sources
		
		numSources(sd,larvaNum) = sum(sources > 0);
		
		
		disp(['File ' num2str(sd) ' ' num2str(larvaNum)]);
		
	end
end
