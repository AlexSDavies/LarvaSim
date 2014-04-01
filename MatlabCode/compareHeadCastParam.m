function mixProp = compareHeadCastParam(filenames)


	% Check Guassian mixture models
	% complain to Tom if it doesn't work

	n = length(filenames);
	
	
	for i = 1:n
		
		params = getHeadCastParams(filenames{i});

		mixProp(:,i) = params(:,1);
		
		castMean(:,i) = (abs(params(:,2)) + fliplr(abs(params(:,3))))./2;
		
		castVar(:,i) = (params(:,4) + fliplr(params(:,5)))./2;

		propTurns(:,i) = params(:,6);

	end
		
	
	
	divs = -180+11.25:22.5:180-11.25;
	
	figure;
	
	subplot(4,1,1); hold on;
	plot(divs,propTurns);
	ylim([0,0.2]);
	title('Proportion of turns');
	legend(filenames);
	
	subplot(4,1,2); hold on;
	plot(divs,mixProp);
	plot(xlim,[0.5 0.5],'-k');
	ylim([0,1]);
	title('Mixing proportion');
	legend(filenames);

	subplot(4,1,3); hold on;
	plot(divs,castMean);
	plot(xlim,[0 0],'-k');
	title('Mean');
	legend(filenames);
	
	subplot(4,1,4); hold on;
	plot(divs,castVar);
	plot(xlim,[0 0],'-k');
	title('Variance');
	legend(filenames);

	
	
	%% Fit sin curves to mixProps
	
	for i = 1:n
		
		sinFit = fit(divs',mixProp(:,i),'0.5+0.5*a*sind(x)');
		sinScaling(i) = sinFit.a
		
	end
	
	figure;
	
	for i = 1:n
		
		subplot(1,n,i); hold on;
		
		plot(divs,mixProp(:,i));
		plot(divs,0.5+0.5*sinScaling(i)*sind(divs),'-r');
		plot([-180,180],[0.5,0.5],'-k');
		ylim([0,1]);
		
	end
	
	
	
	