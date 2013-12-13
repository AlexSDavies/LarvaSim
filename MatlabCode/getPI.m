function PI = getPI(uniqueName,num)

for i = 1:num
	
	fileName = ['data_' uniqueName num2str(i)];
	rawData = dlmread(['../Data/' fileName], ' ',2,0);
	simData = simDataToStruct(rawData);
	
	%% PI Index
	
	leftCount = sum(simData.midPos(:,1) < (0 - 10));
	rightCount = sum(simData.midPos(:,1) > (0 + 10));

	PI(i) = (leftCount-rightCount)/length(simData.midPos);
	
end
