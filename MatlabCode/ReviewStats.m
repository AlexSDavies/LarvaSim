names = {
	

% 'basicModel'
% 'weathervane'
% 'lowCastBase'
% 'highCastKernel'
% 'midHighCastkernel'
% 'lowCastKernel'
% 'midLowCastKernel'
% 'highTurnKernel'
% 'midHighTurnKernel'
% 'lowTurnKernel'
% 'midLowTurnKernel'
% 'noTurnBias'
% 'noCastBias'                                                                                                                                                                                                                                                                                                                
% 'noCastBiasForward5'
% 'noNormalisation'
% 
% 'StatLarva'
% 'StatLarvaNoTurnBias'
% 'StatLarvaNoCastBias'
% 'StatLarvaNoBias'

'cast_turn_wv'
%'cast_turn'
%'cast_wv'
%'turn_wv'
'cast'
'turn'
'wv'
'random'

};

for i = 1:length(names)
	
	name = names{i};
	
	disp(name);
	
	[meanStats variances] = getMultiStats(name,200);
	
	% plotStats(meanStats,['D:\Dropbox\Uni\PhD\ReviewFigures\' name '\']);
	
	meanDist(i,:) = distToOdour(meanStats.data.paths,[-4.1,5.3]);
	
	close all;
	
	
end

figure; hold all;
for i = 1:length(names)

	plot(meanDist(i,:));
	
end

legend(names);

%%
%  
% larvaStats = getLarvaStats();
% plotStats(larvaStats,'D:\Dropbox\Uni\PhD\ReviewFigures\larva\');
% 
% close all;