 
% vars = [5 10 25 50 100];
% 
% normCI(1,:) = getPI('NormalisationPIvar5',50);
% normCI(2,:) = getPI('NormalisationPIvar10',50);
% normCI(3,:) = getPI('NormalisationPIvar25',50);
% normCI(4,:) = getPI('NormalisationPIvar50',50);
% normCI(5,:) = getPI('NormalisationPIvar100',50);
% 
% noNormCI(1,:) = getPI('NoNormalisationPIvar5',50);
% noNormCI(2,:) = getPI('NoNormalisationPIvar10',50);
% noNormCI(3,:) = getPI('NoNormalisationPIvar25',50);
% noNormCI(4,:) = getPI('NoNormalisationPIvar50',50);
% noNormCI(5,:) = getPI('NoNormalisationPIvar100',50);
% 
% %%
% normPI(1,:) = getEndPI('NormalisationPIvar5',5,10);
% normPI(2,:) = getEndPI('NormalisationPIvar10',5,10);
% normPI(3,:) = getEndPI('NormalisationPIvar25',5,10);
% normPI(4,:) = getEndPI('NormalisationPIvar50',5,10);
% normPI(5,:) = getEndPI('NormalisationPIvar100',5,10);
% 
% noNormPI(1,:) = getEndPI('NoNormalisationPIvar5',5,10);
% noNormPI(2,:) = getEndPI('NoNormalisationPIvar10',5,10);
% noNormPI(3,:) = getEndPI('NoNormalisationPIvar25',5,10);
% noNormPI(4,:) = getEndPI('NoNormalisationPIvar50',5,10);
% noNormPI(5,:) = getEndPI('NoNormalisationPIvar100',5,10);


% for v = 1:5
% 
% 	var = vars(v);
% 
% 	% figure;
% 	
% 	subplot(5,3,3*(v-1)+1); title('Environment');
% 	imshow(['D:\Dropbox\Uni\PhD\Review Figures\Pis\Var' num2str(var) '.png']);
% 	
% 	subplot(5,3,3*(v-1)+2); title('Preference Index (end of run)');
% 	boxplot([normPI(v,:)' noNormPI(v,:)'],'labels',{'Normalisation','No Normalisation'});
% 	hold on; plot([0,3],[0,0], '--k'); ylim([-1,1]); ylabel('PI');
% 
% 	subplot(5,3,3*(v-1)+3); title('Chemotaxis Index (whole run)');
% 	boxplot([normCI(v,:)' noNormCI(v,:)'],'labels',{'Normalisation','No Normalisation'});
% 	hold on; plot([0,3],[0,0], '--k'); ylim([-1,1]); ylabel('CI');
% 	
% 	% set(gcf,'Position',[550 646 1193 400]);
% 	
% 	
% end


%%

intensities = [0.2 0.4 0.6 0.8 1.0];
variances = [10 20 30 40 50];

iN = length(intensities);
vN = length(variances);

f1 = figure; title('Preference Index (end of run)');
f2 = figure; title('Chemotaxis Index (whole run)');
f3 = figure;

for i = 1:length(intensities)
	for v = 1:length(variances)
	
	disp(['Intensity: ' num2str(intensities(i)) ' Variance: ' num2str(variances(i))])
	
	name = ['Pref_BasicModel_Intensity' num2str(intensities(i),'%2.1f') '_Variance' num2str(variances(v),'%2.1f') '_'];
	
	% [stats vars] = getMultiStats(name,50);

	% intensity = intensities(i);

	
% 	endPI = getEndPI(name,20,10);
% 	
% 	figure(f1);
%  	subplot(iN,vN,(i-1)*vN + v);
%  	boxplot(endPI,'labels',{''});
%  	hold on; plot([0.5,1.5],[0,0], '--k');
% 	ylim([-1,1]);
% 	xlim([0.5,1.5]); set(gca,'xcolor',[1 1 1]);
% 	set(gca,'XTick',[]); set(gca,'YTick',[-1,0,1]);
% 	box off;
% 	if i == 1; title({'Variance',num2str(variances(v))});  else title({'',''}); end
% 	if v == 1; ylabel({'Intensity',num2str(intensities(i))}); else ylabel({'',''}); end
	
	runPI = getPI(name,50);
	
	figure(f2);
 	subplot(iN,vN,(i-1)*vN + v);
 	boxplot(runPI,'labels',{''});
 	hold on; plot([0.5,1.5],[0,0], '--k');
	ylim([-1,1]);
	xlim([0.5,1.5]); set(gca,'xcolor',[1 1 1]);
	set(gca,'XTick',[]); set(gca,'YTick',[-1,0,1]);
	box off;
	if i == 1; title({'Variance',num2str(variances(v))});  else title({'',''}); end
 	if v == 1; ylabel({'Intensity',num2str(intensities(i))}); else ylabel({'',''}); end
	
% 	figure(f3);
% 	subplot(iN,vN,(i-1)*vN + v);
% 	im = imread(['D:\\Uni\LarvaSim\Data\path_' name '50.png']); axis tight;
% 	imshow(im);
% 	% title(['I:' num2str(intensities(i)) ' V:' num2str(variances(v))]);
% 	if i == 1; title({'Variance',num2str(variances(v))});  else title({'',''}); end
% 	if v == 1; ylabel({'Intensity',num2str(intensities(i))}); else ylabel({'',''}); end
% 	
% 	
% 	
	end
end

%figure(f1); set(gcf,'Position',[0 0 1000 1000]);
%saveeps('D:\Dropbox\Uni\PhD\ReviewFigures\PIs\','AllPIsBasicModel');

figure(f2); set(gcf,'Position',[0 0 1000 1000]);
saveeps('D:\Dropbox\Uni\PhD\ReviewFigures\PIs\','AllRunPIsBasicModel');

% figure(f3); set(gcf,'Position',[0 0 1000 1000]);
% saveeps('D:\Dropbox\Uni\PhD\ReviewFigures\PIs\','Landscapes');


%%

% subplot(2,1,1);
% boxplot(endPI','labels',intensities);
% title('Preference Index (end of run)'); xlabel('Intensity');
% hold on; plot([0,length(intensities)],[0,0], '--k'); ylim([-1,1]); ylabel('PI');
% 
% subplot(2,1,2);
% boxplot(runPI','labels',intensities);
% title('Preference Index (wholerun)'); xlabel('Intensity');
% hold on; plot([0,length(intensities)],[0,0], '--k'); ylim([-1,1]); ylabel('PI');
% 
% %%
% 
% figure('position',[150 700 1400 200]);
% 
% for i = 1:length(intensities)
% 
% 	subplot(1,length(intensities),i);
% 	
% 	positionHeatMap(stats{i}.data.midPos,[-50 50],[-50,50],2);
% 	
% 	title(['Intensity: ' num2str(intensities(i))]);
% 	
% end
% 




