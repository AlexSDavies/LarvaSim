function distPath = equalisePathSegments(path,l)

	xSteps = path(2:end,1) - path(1:end-1,1);
	ySteps = path(2:end,2) - path(1:end-1,2);
	stepLengths = sqrt(xSteps.^2 + ySteps.^2);
	cumStepLengths = [0; cumsum(stepLengths,1)];
	
	
	numPoints = round(cumStepLengths(end)/l);
	
	distSteps = linspace(0,cumStepLengths(end),numPoints);
	
	distPath = interp1(cumStepLengths,path,distSteps);