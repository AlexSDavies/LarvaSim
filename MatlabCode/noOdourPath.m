%%

clear turnsPerSecond
clear numCasts

headAngles = [];

for i = 1:length(kinData_WTxTNT)
% for i = 5:5
	[turnsPerSecond(i) numCasts(i,:) forwardIntervalsL{i} headAngle] = processPath(kinData_WTxTNT{i},0);
	headAngles = [headAngles headAngle];
end

disp(['Mean turns per minute: ' num2str(mean(turnsPerSecond)*60)]);

figure;
bar(mean(numCasts)./sum(mean(numCasts)));



%% Process no-stimuli simulation data

clear turnsPerSecond
clear numCasts

for i = 1:1
	
	disp(i)
	
	stats = getSimStats(['NoStimuli' num2str(i)],0.1);
	data = stats.data;
	
	path.centroid = data.midPos;
	path.head = data.headPos;
	path.tail = data.tailPos;
	
	[turnsPerSecond(i) numCasts(i,:) forwardIntervalsS{i}] = processPath(path,1);
	
end

disp(['Mean turns per minute: ' num2str(mean(turnsPerSecond)*60)]);

figure;
bar(mean(numCasts)./sum(mean(numCasts)));