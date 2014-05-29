function paths = getLinearPaths(modelDataFiles);

xlims = [441 1084];
ylims = [163 1167];

w = xlims(2) - xlims(1);
h = ylims(2) - ylims(1);

n = 90;

%% Get larva paths

larvaDataPath = 'D:\Uni\LarvaSim\Input Data\NatNeuro, raw tracks\NatNeuro, raw tracks\Or42a stochastic rescues (bilateral)\';

folders = {'Exp line gradient\bilateral'
	'Steep-linear line gradient\bilateral'
	'Shallow-linear line gradient\bilateral'};


for f = 1:3
	for i = 1:n
		data = load([larvaDataPath folders{f} '\xys_' int2str(i) '.mat'],'trimed_vect_out_interpolated_temp');
		path = data.trimed_vect_out_interpolated_temp(:,1:2);
		path(:,1) = path(:,1) - ylims(1);
		path(:,2) = path(:,2) - xlims(1);
		paths{1,f,i} = path;
	end
end


%% Get model paths

modelDataPath = '../Data/data_';

% modelDataFiles = {
% 	'ExpTest'
% 	'LinSteepTest'
% 	'LinShallTest'
% 	'ExpTestWV'
% 	'LinSteepTestWV'
% 	'LinShallTestWV'
% 	'ExpTestHighKernel'
% 	'LinSteepTestHighKernel'
% 	'LinShallTestHighKernel'
% 	'ExpTestHighKernelWV'
% 	'LinSteepTestHighKernelWV'
% 	'LinShallTestHighKernelWV'
% 	'ExpTestNN'
% 	'LinSteepTestNN'
% 	'LinShallTestNN'
% 	'ExpTestNNHighKernel'
% 	'LinSteepTestNNHighKernel'
% 	'LinShallTestNNHighKernel'
% 	};



for f = 1:length(modelDataFiles)
	disp(f);
	for i = 1:n
		m = floor((f-1)/3)+2;
		c = mod(f-1,3)+1;
		rawData = dlmread([modelDataPath modelDataFiles{f} int2str(i)], ' ',2,0);
		data = simDataToStruct(rawData);
		path = data.tailPos(1:10:end,:);
		path(:,1) = path(:,1).*10 + h/2;
		path(:,2) = path(:,2).*10 + w/2;
		paths{m,c,i} = path;
	end
end


end