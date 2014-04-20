function compareLinearPaths(paths,models,n)

% Only work with specified amount of paths data
paths = paths(models,:,1:n);

% For drawing
xlims = [441 1084];
ylims = [163 1167];

height = xlims(2) - xlims(1);
width = ylims(2) - ylims(1);

pathdraw = 0;

%% Get stats for each path

[noModels noConditions ~] = size(paths);

for m = 1:noModels
	for c = 1:noConditions
		for i = 1:n
			
			path = paths{m,c,i};
			
			% Angles
			angles{m,c,i} = getPathAngles(path);

			dAngle{m,c,i} = normaliseAngle(diff(angles{m,c,i}));
			
			% Path length / angle stuff
			cosAngle{m,c,i} = mean(cos(angles{m,c,i}));
			sinAngle{m,c,i} = mean(sin(angles{m,c,i}));
			
			xSteps = path(2:end,1) - path(1:end-1,1);
			ySteps = path(2:end,2) - path(1:end-1,2);
			stepLengths = sqrt(xSteps.^2 + ySteps.^2);
			pathLength = sum(stepLengths);
			
			xDist{m,c,i} = sum(xSteps)/pathLength;
			yDist{m,c,i} = sum(ySteps)/pathLength;
			
			vectorAngle{m,c,i} = atan2(yDist{m,c,i},xDist{m,c,i});
			vectorLength{m,c,i} = sqrt(yDist{m,c,i}^2 + xDist{m,c,i}^2);
					
			% Time to right hand edge
			thresh = 0.9;
			t = find(path(:,1) > width*thresh,1);
			if(isempty(t))
				t = length(path);
			end
			distToOdour{m,c,i} = sum(stepLengths(1:t-1));
			% timeToOdour{m,c,i} = t;
			
		end
		
		hist(dAngle{m,c,i},20);
		
	end
end


%% Get averages
for m = 1:noModels
	for c = 1:noConditions
		
		averageVectorLength{m,c} = mean([vectorLength{m,c,:}]);
		averageVectorAngle{m,c} = atan2(sum([yDist{m,c,:}]),sum([xDist{m,c,:}]));
		averageVectorX{m,c} = mean([xDist{m,c,:}]);
		averageVectorY{m,c} = mean([yDist{m,c,:}]);
		
	end
end



%% Plot

vectorPathFig = figure;
hold on;

vectorLengthFig = figure;

distToOdourFig = figure;

for m = 1:noModels
	for c = 1:noConditions
		
		subplotInd = (m-1)*noConditions+c;
		
		figure(vectorPathFig);
		vectorPathAxes = subplot(noModels,noConditions,subplotInd);
		hold on;
			
		figure(vectorLengthFig);
		vectoLengthAxes = subplot(noModels,noConditions,subplotInd);
		
		figure(distToOdourFig);
		distToOdourAxes = subplot(noModels,noConditions,subplotInd);
		
		if pathdraw > 0
			pathFig = figure;
			set(gcf,'Name',['Model: ' num2str(m) '   Condition: ' num2str(c)])
		end
		
		for i = 1:n
	
			plot(vectorPathAxes,[0 xDist{m,c,i}],[0,yDist{m,c,i}],'-k');
			
			if (i <= pathdraw)
				figure(pathFig);
				subplot(sqrt(pathdraw),sqrt(pathdraw),i);
				plot(paths{m,c,i}(:,1),paths{m,c,i}(:,2));
				xlim([0 width]); ylim([0 height]); set(gca,'XTick', [], 'YTick', []);
			end
			
		end
		
		plot(vectorPathAxes,[0 averageVectorX{m,c}],[0,averageVectorY{m,c}],'-r','linewidth',4);
		axis(vectorPathAxes,[-1,1,-1,1],'equal');
		
		boxplot(vectoLengthAxes,[vectorLength{m,c,:}]);
		ylim(vectoLengthAxes,[-1,1]);
		
		boxplot(distToOdourAxes,[distToOdour{m,c,:}]);
		ylim(distToOdourAxes,[0,2500]);
		
	end
end



%% Stats

sig = 0.001;

statChecks = {'cosAngle','sinAngle','vectorLength','distToOdour'};

for s = 1:length(statChecks)

	stat = eval([statChecks{s}]);
	
	disp(statChecks{s});
	
	for m = 1:noModels

		disp(['Model ' num2str(m)]);

		sig1 = '';
		expVsSteep = ranksum([stat{m,1,:}],[stat{m,2,:}]);
		if expVsSteep < sig sig1 = '***'; end
		
		sig2 = '';
		steepVsShall = ranksum([stat{m,2,:}],[stat{m,3,:}]);
		if steepVsShall < sig sig2 = '***'; end
		
		fprintf('exp -> steep\tsteep -> shall\n%s%08f\t\t%s%08f\nn',sig1,expVsSteep,sig2,steepVsShall);
		% fprintf('%08f\n',expVsSteep);
		% disp('Steep / shall, p = ');
		% fprintf('%0.8f\n',steepVsShall);

		
	end
	
	fprintf('\n\n');
		
end




