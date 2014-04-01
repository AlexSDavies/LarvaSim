

figure; hold on;
boxplot(iPIsNorm(:,1:9),'labels',{'0.8','0.6','0.4','0.2','0.0','-0.2','-0.4','-0.6','-0.8'});
plot(xlim,[0,0],'-k');
title('No norm, whole run');
ylim([-1,1]);

figure; hold on;
boxplot(ePIsNorm(:,1:9),'labels',{'0.8','0.6','0.4','0.2','0.0','-0.2','-0.4','-0.6','-0.8'});
plot(xlim,[0,0],'-k');
title('No norm, end of run');
ylim([-1,1]);


figure; hold on;
boxplot(iPIsNoNorm(:,1:9),'labels',{'0.8','0.6','0.4','0.2','0.0','-0.2','-0.4','-0.6','-0.8'});
plot(xlim,[0,0],'-k');
title('No norm, whole run');
ylim([-1,1]);


figure; hold on;
boxplot(ePIsNoNorm(:,1:9),'labels',{'0.8','0.6','0.4','0.2','0.0','-0.2','-0.4','-0.6','-0.8'});
plot(xlim,[0,0],'-k');
title('No norm, end of run');
ylim([-1,1]);



%% 

for i = 0:6
	
	PIs(i+1,:,:) = comparePIs(['KernelPrefNorm_' num2str(i) '_'],[0:6],50,'Kernel Scaling');
	
	for j = 0:6
		simStats(i+1,j+1) = getMultiStats(['KernelPrefNorm_' num2str(i) '_' num2str(j) '_'],50);
	end
	
end


%%
for i = 1:7
	subplot(7,1,i); hold on;
	boxplot(squeeze(PIs(i,:,:)));
	plot(xlim,[0,0],'-k');
end


%%

for i = 1:7
	
	subplot(7,1,i); hold on;
	b = boxplot(squeeze(PIs(i,:,1:7)),'labels',{'','','','','','',''},'colors','k','symbol','k');
	
	for j = 1:7
		
		boxLines = squeeze(b(:,j));
				
		faceVertsX = get(boxLines(5),'XData');
		faceVertsY = get(boxLines(5),'YData');
		xVerts = faceVertsX([1,3]);
		yVerts = faceVertsY([1,2]);
		
		p = patch(faceVertsX,faceVertsY,[0.6 0.6 0.6]);
		
		uistack(p,'bottom')
		
	end
	
	ylim([-1.,1]);
	plot(xlim,[0,0],'-k'); plot(xlim,[1,1],'-k'); plot(xlim,[-1,-1],'-k');
	
	set(gca,'YTick',[]);
	
end

saveeps('D:\Dropbox\Uni\Presentations\Atami Poster 2014\','AllPIs')



%%

figure;

scale = [1 2/3 1/3 0 -1/3 -2/3 -1];

for i = 1:7

	subplot(1,7,i); hold on;

	plot([0,20],[1,-1].*scale(i),'-r','linewidth',2);
	plot([0,20],[0,0],'-k');
	ylim([-1,1]);

	set(gca,'YTick',[],'XTick',[]);

end

set(gcf,'Position',[100 100 960 145]);

saveeps('D:\Dropbox\Uni\Presentations\Atami Poster 2014\','Run Kernels');



figure;

for i = 1:7

	subplot(7,1,i); hold on;

	plot([0,4,5],[0,0,1].*scale(i),'-r','linewidth',2);
	plot([0,5],[0,0],'-k');
	ylim([-1,1]); xlim([0,5]);

	set(gca,'YTick',[],'XTick',[]);

end

set(gcf,'Position',[100 100 145 960]);

saveeps('D:\Dropbox\Uni\Presentations\Atami Poster 2014\','Cast Kernels');





