function turnIndeces = getTurns(head,mid,tail,timestep)

n = length(mid);

deltaAngle = zeros(1,n);
headAngle = zeros(1,n);
tailAngle = zeros(1,n);

for i = 1:n
	
	headVec = head(i,:)-mid(i,:);
	tailVec = mid(i,:)-tail(i,:);
	
	headAngle(i) = atan2(headVec(2),headVec(1));
	tailAngle(i) = atan2(tailVec(2),tailVec(1));
	
	if(i > 1)
		deltaAngle(i) = normaliseAngle(tailAngle(i) - tailAngle(i-1));
	end
	
end

%% Find turns
deltaAngleThresh = deg2rad(12) * timestep;

turnSteps = abs(deltaAngle) > deltaAngleThresh;
turnEndpoints = diff([0 turnSteps 0]);
turnStarts = find(turnEndpoints > 0);
turnEnds = find(turnEndpoints < 0)-1;
turnLengths = ((turnEnds - turnStarts)+1)*0.1;

% Filter to > 1 sec
turnStarts = turnStarts(turnLengths > 1)';
turnEnds = turnEnds(turnLengths > 1)';

turnIndeces = [turnStarts turnEnds];
