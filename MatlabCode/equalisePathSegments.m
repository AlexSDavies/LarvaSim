function newPath = equalisePathSegments(path,l)

	path = unique(path,'rows','stable');

	xSteps = diff(path(:,1));
	ySteps = diff(path(:,2));
	stepLengths = sqrt(xSteps.^2 + ySteps.^2);
	
	pathLength = [0; cumsum(stepLengths,1)];
	
	numSegs = floor(pathLength(end)/l);
	
	distSteps = (0:numSegs)*l;
	
	% distPath = interp1(cumStepLengths,path,distSteps);
	
	segmentPoints = interp1(pathLength,0:length(path)-1,distSteps);
	
	newPath = [];

	path = [path; path(end,:)];
	
	for i = 1:numSegs+1
		
		p = floor(segmentPoints(i))+1;
		f = segmentPoints(i) - floor(segmentPoints(i));
		
		prevPathPoint = path(p,:);
		nextPathPoint = path(p+1,:);
		
		newPoint = prevPathPoint + f*(nextPathPoint - prevPathPoint);
		
		newPath = [newPath; newPoint];
		
	end
	