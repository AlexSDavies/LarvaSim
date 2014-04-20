%%

xlims = [441 1084];
ylims = [163 1167];

w = xlims(2) - xlims(1);
h = ylims(2) - ylims(1);

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
	
	figure;

	for i = 1:9

		disp(i)

		data = load([dataPath folder '\xys_' int2str(i) '.mat'],'trimed_vect_out_interpolated_temp');

		path = data.trimed_vect_out_interpolated_temp(:,1:2);

		subplot(3,3,i); hold on;
		
		imagesc(gradient(xlims(1):xlims(2),ylims(1):ylims(2)));
		alpha(0.7);
		
		plot(path(:,1)-ylims(1),path(:,2)-xlims(1),'-k','linewidth',2);
		
		xlim([0,h]); ylim([0,w]);
		
		% print(gcf,['D:\Dropbox\Uni\PhD\LinearPaths\' name int2str(i) '.png'],'-dpng');
		
		% close(gcf);

		% allPaths = [allPaths; path];

	end

	print(gcf,['D:\Dropbox\Uni\PhD\LinearPaths\' name '.png'],'-dpng');
	close(gcf);
	
	% positionHeatMap(allPaths,ylims,xlims,40);
	
	% print(gcf,['D:\Dropbox\Uni\PhD\LinearPaths\' name '_heatmap.png'],'-dpng');
	
	
end

%%

names = {'ModelExp' 'ModelLinSteep' 'ModelLinShallow' 'ModelExpWV' 'ModelLinSteepWV' 'ModelLinShallowWV' 'ModelExpHighKernel' 'ModelLinSteepHighKernel' 'ModelLinShallowHighKernel' 'ModelExpHighKernelWV' 'ModelLinSteepHighKernelWV' 'ModelLinShallowHighKernelWV'};
dataFiles = {'ExpTest' 'LinSteepTest' 'LinShallTest' 'ExpTestWV' 'LinSteepTestWV' 'LinShallTestWV' 'ExpTestHighKernel' 'LinSteepTestHighKernel' 'LinShallTestHighKernel' 'ExpTestHighKernelWV' 'LinSteepTestHighKernelWV' 'LinShallTestHighKernelWV'};
gradientFiles = {'ExpGrad.mat' 'SteepLinGrad.mat' 'ShallowLinGrad.mat' 'ExpGrad.mat' 'SteepLinGrad.mat' 'ShallowLinGrad.mat' 'ExpGrad.mat' 'SteepLinGrad.mat' 'ShallowLinGrad.mat' 'ExpGrad.mat' 'SteepLinGrad.mat' 'ShallowLinGrad.mat'};

for f = 1:12

% 	for t = 1:4

		% * 
		name = names{f};

		% *
		gradientFile = ['D:\Uni\LarvaSim\Input Data\Gradients\Gradients\' gradientFiles{f}];
		gradData = load(gradientFile,'gradient_mat');
		gradient = gradData.gradient_mat;

		dataName = dataFiles{f};

		allPaths = [];

		for i = 1:9

			rawData = dlmread(['../Data/data_' dataName int2str(i)], ' ',2,0);
			data = simDataToStruct(rawData);

			midPos = data.midPos.*10;
			midPos(:,1) = midPos(:,1) + 1004/2;
			midPos(:,2) = midPos(:,2) + 643/2;

			
			
			
			% figure;
			
			subplot(3,3,i); hold on;
			
			imagesc(gradient(xlims(1):xlims(2),ylims(1):ylims(2)));
			alpha(0.7);	
			
			plot(midPos(:,1),midPos(:,2),'-k','linewidth',2);
			
			xlim([0,h]); ylim([0,w]);
			
			% print(gcf,['D:\Dropbox\Uni\PhD\LinearPaths\' name int2str(i) '.png'],'-dpng');
			
			% close(gcf);

% 			l = length(midPos);
% 			d = l/4;
% 			startT = round((t-1) * d) + 1;
% 			endT = round(t*d);
	
			% allPaths = [allPaths; midPos];
			% allPaths = [allPaths; midPos(startT:endT,:)];


% 		end
		
% 		subplot(1,4,t);
% 		positionHeatMap(allPaths,ylims,xlims,40);

		end
	
		print(gcf,['D:\Dropbox\Uni\PhD\LinearPaths\' name '.png'],'-dpng');
		close(gcf);
	
% 	print(gcf,['D:\Dropbox\Uni\PhD\LinearPaths\' name '_T' num2str(t) '_heatmap.png'],'-dpng');
	
end




%% Bearings

% Larva

for f = 1:3

	
	averageAngle = [];
	
	for i = 1:90

		data = load([dataPath folders{f} '\xys_' int2str(i) '.mat'],'trimed_vect_out_interpolated_temp');

		path = data.trimed_vect_out_interpolated_temp(:,1:2);

		averageAngle(i) = mean(cos(getPathAngles(path)));
		
		% pathLength = sum(sqrt(sum((path(2:end,:) - path(1:end-1,:)).^2,2)));
		% vector(i,:) = sum(path(2:end,:) - path(1:end-1,:))./pathLength;
		
		
	end

	% averageVector(f,:) = mean(vector);
	
	subplot(5,3,f);
	
	boxplot(averageAngle);

	ylim([-1.0,1.0]); hold on; plot(xlim,[0 0],'--k');

	averageAnglesLarva{f} = averageAngle;
	
end


% figure; hold on;
% for i = 1:3
% plot([0,averageVector(:,1)],[0,averageVector(:,2)]);
%e nd

%%  Model

for f = 1:12

	averageAngle = [];
	
	for i = 1:90

		rawData = dlmread(['../Data/data_' dataFiles{f} int2str(i)], ' ',2,0);
		data = simDataToStruct(rawData);
		
		averageAngle(i) = mean(cos(getPathAngles(data.midPos)));
		
	end

	subplot(5,3,f+3);

	boxplot(averageAngle);

	ylim([-1.0,1.0]); hold on; plot(xlim,[0 0],'--k');

	averageAnglesModel{f} = averageAngle;
	
end

