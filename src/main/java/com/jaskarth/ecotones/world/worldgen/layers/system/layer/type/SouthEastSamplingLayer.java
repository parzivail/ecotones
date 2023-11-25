package com.jaskarth.ecotones.world.worldgen.layers.system.layer.type;

import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampleContext;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampler;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.NorthWestCoordinateTransformer;

public interface SouthEastSamplingLayer extends ParentedLayer, NorthWestCoordinateTransformer {
   int sample(LayerRandomnessSource context, int se);

   @Override
   default int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
      int i = parent.sample(this.transformX(x + 1), this.transformZ(z + 1));
      return this.sample(context, i);
   }
}