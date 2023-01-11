package de.fhws.fiw.fds.sutton.server.api.states.post;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * <p>The AbstractPostRelationState extends the {@link AbstractPostState} with the required
 * functionality to create a sub-resource in the database and to create a relation between the created sub-resource
 * and its primary resource.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend
 * {@link AbstractPostRelationState.AbstractPostRelationStateBuilder}</p>
 *
 * @see AbstractPostState
 * */
public abstract class AbstractPostRelationState<T extends AbstractModel> extends AbstractPostState<T>
{
	/**
	 * id {@link Long} of the primary resource
	 * */
	protected long primaryId;

	public AbstractPostRelationState( final AbstractPostRelationStateBuilder builder )
	{
		super( builder );
		this.primaryId = builder.parentId;
	}

	public static abstract class AbstractPostRelationStateBuilder<T extends AbstractModel>
		extends AbstractPostStateBuilder<T>
	{
		protected long parentId;

		public AbstractPostRelationStateBuilder setParentId( final long parentId )
		{
			this.parentId = parentId;
			return this;
		}
	}
}
