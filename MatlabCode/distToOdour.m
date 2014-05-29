function meanDists = distToOdour(paths,odourPos)

% Min number of larvae to use for an average
minLarvae = 10;

% Every 10s
timePoints = (10:10:300)*10-1;

for i = 1:length(paths)
	
	% NaN pad path to full length
	path = NaN(2999,2); 
	path(1:length(paths{i}),:) = paths{i};
	
	positions = path(timePoints,:);
	
	distances(i,:) = sqrt(sum(bsxfun(@minus,positions,odourPos).^2,2));

	
end

meanDists = nanmean(distances);

for t = 1:length(timePoints)
	if sum(~isnan(distances(:,t))) < minLarvae
		meanDists(:,t) = NaN;
	end
end