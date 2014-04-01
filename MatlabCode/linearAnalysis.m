%%

xlims = [441 1084];
ylims = [163 1167];

%%

% expGrad = expGrad(xlims(1):xlims(2),ylims(1):ylims(2));
% shallLinGrad = shallLinGrad(xlims(1):xlims(2),ylims(1):ylims(2));
% steepLinGrad = steepLinGrad(xlims(1):xlims(2),ylims(1):ylims(2));

%%

max(expGrad(:))
max(shallLinGrad(:))
max(steepLinGrad(:))

maxConc = max([max(expGrad(:)) max(shallLinGrad(:)) max(steepLinGrad(:))]);

expGradNorm = expGrad ./ maxConc;
shallLinGradNorm = shallLinGrad ./ maxConc;
steepLinGradNorm = steepLinGrad ./ maxConc;

figure; imagesc(expGradNorm,[0,1]);
figure; imagesc(shallLinGradNorm,[0,1]);
figure; imagesc(steepLinGradNorm,[0,1]);

%%

csvwrite('D:\Uni\LarvaSim\Input Data\expNorm.csv',expGradNorm);
csvwrite('D:\Uni\LarvaSim\Input Data\linShallNorm.csv',shallLinGradNorm);
csvwrite('D:\Uni\LarvaSim\Input Data\linSteepNorm.csv',steepLinGradNorm);


%%
names = {'LarvaExp' 'LarvaLinSteep' 'LarvaLinShallow'};
folders = {'Exp line gradient\bilateral' 'Steep-linear line gradient\bilateral' 'Shallow-linear line gradient\bilateral'};
gradientFiles = {'ExpGrad.mat' 'SteepLinGrad.mat' 'ShallowLinGrad.mat'};

xlims = [441 1084];
ylims = [163 1167];

for f = 1:3

	% *
	name = names{f};

	dataPath = 'D:\Uni\LarvaSim\Input Data\NatNeuro, raw tracks\NatNeuro, raw tracks\Or42a stochastic rescues (bilateral)\';

	% *
	folder = folders{f};

	% *
	gradientFile = ['D:\Uni\LarvaSim\Input Data\Gradients\Gradients\' gradientFiles{f}];

	gradData = load(gradientFile,'gradient_mat');

	gradient = gradData.gradient_mat;

	allPaths = [];

	for i = 1:90

		disp(i)

		data = load([dataPath folder '\xys_' int2str(i) '.mat'],'trimed_vect_out_interpolated_temp');

		path = data.trimed_vect_out_interpolated_temp(:,1:2);

	% 	figure;
	% 	
	% 	imagesc(gradient);
	% 	alpha(0.7);
	% 	
	% 	hold on;
	% 	plot(path(:,1),path(:,2),'-k','linewidth',2);
	% 	
	% 	% print(gcf,['D:\Dropbox\Uni\PhD\LinearPaths\' name int2str(i) '.png'],'-dpng');
	% 	
	% 	close(gcf);

		allPaths = [allPaths; path];

	end

	positionHeatMap(allPaths,ylims,xlims,40);
	
	print(gcf,['D:\Dropbox\Uni\PhD\LinearPaths\' name '_heatmap.png'],'-dpng');
	
	
end

%%

names = {'ModelExp' 'ModelLinSteep' 'ModelLinShallow' 'ModelExpWV' 'ModelLinSteepWV' 'ModelLinShallowWV' };
dataFiles = {'ExpTest' 'LinSteepTest' 'LinShallTest' 'ExpTestWV' 'LinSteepTestWV' 'LinShallTestWV'};
gradientFiles = {'ExpGrad.mat' 'SteepLinGrad.mat' 'ShallowLinGrad.mat' 'ExpGrad.mat' 'SteepLinGrad.mat' 'ShallowLinGrad.mat'};

for f = 1:3

	for t = 1:4
	
		% * 
		name = names{f};

		% *
		gradientFile = ['D:\Uni\LarvaSim\Input Data\Gradients\Gradients\' gradientFiles{f}];
		gradData = load(gradientFile,'gradient_mat');
		gradient = gradData.gradient_mat;

		dataName = dataFiles{f};

		allPaths = [];

		for i = 1:90

			rawData = dlmread(['../Data/data_' dataName int2str(i)], ' ',2,0);
			data = simDataToStruct(rawData);

			midPos = data.midPos.*10;
			midPos(:,1) = midPos(:,1) + 163 + 1004/2;
			midPos(:,2) = midPos(:,2) + 441 + 643/2;


	% 		figure;
	% 		imagesc(gradient);
	% 		alpha(0.7);
	% 		
	% 		hold on;
	% 		plot(midPos(:,1),midPos(:,2),'-k','linewidth',2);
	% 		
	% 		print(gcf,['D:\Dropbox\Uni\PhD\LinearPaths\' name int2str(i) '.png'],'-dpng');
	% 		
	% 		close(gcf);

			l = length(midPos);
			d = l/4;
			startT = round((t-1) * d) + 1;
			endT = round(t*d);
	
			allPaths = [allPaths; midPos(startT:endT,:)];


		end
		
		subplot(1,4,t);
		positionHeatMap(allPaths,ylims,xlims,40);

	end
	
	print(gcf,['D:\Dropbox\Uni\PhD\LinearPaths\' name '_T' num2str(t) '_heatmap.png'],'-dpng');
	
end

