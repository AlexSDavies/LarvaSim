function heatmapWithPaths(paths,xlims,ylims,numPaths)

	allPositions = vertcat(paths{:});

	positionHeatMap(allPositions,xlims,ylims,2);

	hold all;
	
	for i = 1:numPaths
		
		path = paths{i};
		
		plot(path(:,1),path(:,2));
		
	end
	