function headCasts = getHeadCastBearings(file,numPreTurnDivs,numCastAngleDivs)


	data = dlmread(['../Input Data/bearing_and_turning/' file '.csv'],',',2,0);

	
	preTurnDivSize = 360 / numPreTurnDivs;

	turnAngleDivSize = 360 / numCastAngleDivs;
	
	% Split data according to bearing at turn initiation
	for i = 1:numPreTurnDivs

		startAngle(i) = -180 + (i-1) * preTurnDivSize;
		endAngle(i) = -180 + i * preTurnDivSize;
		
		a1 = startAngle(i);
		a2 = endAngle(i);
		
		headCasts{i} = data(data(:,1) > a1 & data(:,1) < a2,2);
		
	end
