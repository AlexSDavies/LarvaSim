names = {
	

'basicModel'
'lowCastBase'
'highCastKernel'
'midHighCastkernel'
'lowCastKernel'
'midLowCastKernel'
'highTurnKernel'
'midHighTurnKernel'
'lowTurnKernel'
'midLowTurnKernel'
'noTurnBias'
'noCastBias'                                                                                                                                                                                                                                                                                                                
'noCastBiasForward5'
'noNormalisation'

};

for i = 1:length(names)
	
	name = names{i};
	
	disp(name);
	
	[meanStats variances] = getMultiStats(name,50);
	
	plotStats(meanStats,['D:\Dropbox\Uni\PhD\ReviewFigures\' name '\']);
	
	close all;
	
	
end


larvaStats = getLarvaStats();
plotStats(larvaStats,'D:\Dropbox\Uni\PhD\ReviewFigures\larva\');

close all;