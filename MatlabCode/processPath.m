function [turnsPerSecond numCasts forwardIntervals maxCastAngle] = processPath(path,varargin)

if ~isempty(varargin)
	showPlot = varargin{1};
else
	showPlot = 0;
end

timestep = 0.1;

% Remove trailing zeros
if path.centroid(end,1) == 0;
	removeIndeces = 1:find(diff(path.centroid(:,1) > 0) == -1, 1, 'last');
	path.centroid = path.centroid(removeIndeces,:);
	path.head = path.head(removeIndeces,:);
	path.tail = path.tail(removeIndeces,:);
end

path.centroid(path.centroid == 0) = NaN;
path.head(path.head == 0) = NaN;
path.tail(path.tail == 0) = NaN;

t = length(path.centroid)*timestep;

centroid = interpolate(path.centroid);
head = interpolate(path.head);
tail = interpolate(path.tail);


n = length(centroid);

for i = 1:n
	
	headVec = head(i,:)-centroid(i,:);
	tailVec = centroid(i,:)-tail(i,:);
	
	headAngle(i) = atan2(headVec(2),headVec(1));
	tailAngle(i) = atan2(tailVec(2),tailVec(1));
	
	relativeHeadAngle(i) = normaliseAngle(headAngle(i) - tailAngle(i));
	
	
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
turnStarts = turnStarts(turnLengths > 1);
turnEnds = turnEnds(turnLengths > 1);
turnLengths = turnLengths(turnLengths > 1);


%% Find head  casts
headAngleThresh = deg2rad(37);

castSteps = abs(relativeHeadAngle) > headAngleThresh;
castEndpoints = diff([0 castSteps 0]);
castStarts = find(castEndpoints > 0);
castEnds = find(castEndpoints < 0)-1;
castLengths = ((castEnds - castStarts)+1)*0.1;

% Filter to > 0.1 sec
castStarts = castStarts(castLengths > 0.1);
castEnds = castEnds(castLengths > 0.1);
castLengths = castLengths(castLengths > 0.1);

for i = 1:length(castStarts)
	maxCastAngle(i) = max(abs(relativeHeadAngle(castStarts(i):castEnds(i))));
end

% Get casts associated with each turn
for i = 1:length(turnStarts)
	
	startTime = max(1,turnStarts(i)-5/timestep);
	endTime = min(length(path.centroid),turnStarts(i)+1/timestep);
	
	turnCasts(i) = sum(castEnds > startTime & castEnds < endTime);
	
end

% Get output stats 

runTime = t - sum(castLengths)*timestep;

turnsPerSecond = length(turnStarts)/runTime;

forwardIntervals = turnStarts(2:end) - turnEnds(1:end-1);

for i = 1:7
	numCasts(i) = sum(turnCasts == i);
end


%% Plot

if(showPlot)

	figure;
	subplot(4,1,1); hold on;
	plot(head(:,1),'-r');
	plot(head(:,2),'-r');
	plot(centroid(:,1));
	plot(centroid(:,2));

	subplot(4,1,2); hold on;
	plot(headAngle);
	plot(tailAngle,'-r');
	legend({'Head angle','Body angle'});

	% Turns

	subplot(4,1,3); hold on;

	for i = 1:length(turnStarts);
		rectangle('position',[turnStarts(i), -0.5, turnEnds(i)-turnStarts(i), 1],'facecolor',[0.9 0.9 0.9],'LineStyle','none');
		ylim([-0.2 0.2]);
	end

	plot(deltaAngle,'-r');
	legend({'da/dt'});

	plot(xlim,[0.029 0.029],'--k');
	plot(xlim,[-0.029 -0.029],'--k');

	% Head cast 

	subplot(4,1,4); hold on;

	for i = 1:length(castStarts);
		rectangle('position',[castStarts(i), -1, castEnds(i)-castStarts(i), 2],'facecolor',[0.9 0.9 0.9],'LineStyle','none');
		ylim([-1 1]);
	end

	plot(relativeHeadAngle);

	plot(xlim,[0.65 0.65],'--k');
	plot(xlim,[-0.65 -0.65],'--k');

end

