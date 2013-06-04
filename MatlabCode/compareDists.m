function [dist1,dist2] = compareDists(name1,name2,num,thresh)

for i = 1:num
	
	disp(i);
	
	simStats1 = getSimStats([name1 '_' num2str(i)],0.1);
	dist1(i) = pathDistToOdour(simStats1,thresh);
	
	simStats2 = getSimStats([name2 '_' num2str(i)],0.1);
	dist2(i) = pathDistToOdour(simStats2,thresh);
	
end

boxplot([dist1' dist2']);

figure; hold on;

for i = 1:50
	
	plot([1 2],[dist1(i) dist2(i)]);
	
end