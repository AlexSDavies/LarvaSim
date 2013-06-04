function PI = getPI(uniqueName,num)

for i = 1:num
	
	fileName = ['data_' uniqueName 'Larva_' num2str(i)];
	rawData = dlmread(['../Data/' fileName], ' ',2,0);
	simData = simDataToStruct(rawData);
	
	%% PI Index
	% Assumes 0 centred arena radius 200

	size = 200;

	leftCount = sum(simData.midPos(:,1) < (0 - size/10));
	rightCount = sum(simData.midPos(:,1) > (0 + size/10));

	PI(i) = (leftCount-rightCount)/length(simData.midPos);
	
end
